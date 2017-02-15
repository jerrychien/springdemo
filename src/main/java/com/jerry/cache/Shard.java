package com.jerry.cache;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * memcached一致性hash算法,当虚拟节点越多的时候，节点的数据分布就越平均
 * <p>
 * 100个虚拟节点
 * node192.168.0.1:11211:251512:0.251512
 * node192.168.0.1:11212:280593:0.280593
 * node192.168.0.1:11213:266413:0.266413
 * node192.168.0.1:11214:201482:0.201482
 * <p>
 * 1000个虚拟节点
 * node192.168.0.1:11211:251888:0.251888
 * node192.168.0.1:11212:242350:0.24235
 * node192.168.0.1:11213:249791:0.249791
 * node192.168.0.1:11214:255971:0.255971
 * <p>
 * 10000个虚拟节点
 * node192.168.0.1:11211:249271:0.249271
 * node192.168.0.1:11212:249836:0.249836
 * node192.168.0.1:11213:247652:0.247652
 * node192.168.0.1:11214:253241:0.253241
 * <p>
 * Created by jerrychien on 2017-02-15 09:56.
 */
public class Shard<T> {

    //每个节点虚拟100次
    private int virtualTimes = 10000;

    // 虚拟节点信息
    private TreeMap<Long, T> virtualNodes;

    // 真实节点信息
    private List<T> realNodes;

    public Shard(List<T> realNodes) {
        this.realNodes = realNodes;
        init();
    }

    private void init() {
        virtualNodes = new TreeMap<Long, T>();
        for (int i = 0; i < realNodes.size(); i++) {
            for (int j = 0; j < virtualTimes; j++) {
                virtualNodes.put(hash("node:" + i + j), realNodes.get(i));
            }
        }
    }

    public TreeMap<Long, T> getTotalNode() {
        return virtualNodes;
    }

    public T getNode(String key) {
        Long hashValue = hash(key);
        //获取TreeMap中不小于key的最小的节点；
        Map.Entry<Long, T> entry = virtualNodes.ceilingEntry(hashValue);
        if (entry == null) {//如果没有大于这个key的节点，说明他的值是目前最大的，按顺时针个顺序获取到第一个节点值
            return virtualNodes.firstEntry().getValue();
        } else {
            return entry.getValue();
        }
    }

    public List<T> getRealNodes() {
        return realNodes;
    }

    /**
     * MurMurHash算法，是非加密HASH算法，性能很高，
     * 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
     * 等HASH算法要快很多，而且据说这个算法的碰撞率很低. http://murmurhash.googlepages.com/
     */
    private Long hash(String key) {
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return h;
    }

    static class Node {

        private String name;

        private int count;

        public Node(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public synchronized void inc() {
            count++;
        }
    }

    public static void main(String[] args) {
        List<Node> realNode = new ArrayList<Node>();
        realNode.add(new Shard.Node("192.168.0.1:11211"));
        realNode.add(new Shard.Node("192.168.0.1:11212"));
        realNode.add(new Shard.Node("192.168.0.1:11213"));
        realNode.add(new Shard.Node("192.168.0.1:11214"));
        final Shard<Node> shard = new Shard(realNode);
//        Iterator<Long> keySet = shard.getTotalNode().keySet().iterator();
//        while (keySet.hasNext()) {
//            System.out.println(keySet.next());
//        }
        final int threadNum = 1000;
        final CountDownLatch cdl = new CountDownLatch(threadNum);
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    // Random rd = new Random(1100);
                    for (int k = 0; k < threadNum; k++) {
                        shard.getNode(String.valueOf(Math.random())).inc();
                    }
                    cdl.countDown();
                }
            });
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("all add down");
        List<Node> nodeList = shard.getRealNodes();
        for (Node node : nodeList) {
            System.out.println("node" + node.getName() + ":" + node.getCount() + ":" + new BigDecimal(node.getCount()).divide(new BigDecimal(threadNum * threadNum)).multiply(new BigDecimal(100)) + "%");
        }
        executorService.shutdownNow();
    }
}
