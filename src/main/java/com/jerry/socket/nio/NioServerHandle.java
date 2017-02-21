package com.jerry.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jerrychien on 2017-02-16 23:45.
 */
public class NioServerHandle implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean started = false;

    private int count = 1;

    private static List<SocketChannel> socketChannelList = new ArrayList<SocketChannel>();

    /*映射客户端channel */
    private Map<String, SocketChannel> clientsMap = new HashMap<String, SocketChannel>();

    public NioServerHandle(int port) {
        try {
            //创建选择器
            selector = Selector.open();
            //打开监听通道
            serverSocketChannel = ServerSocketChannel.open();
            //如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //绑定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            //监听客户端连接
            //服务端接收客户端连接事件	SelectionKey.OP_ACCEPT(16)
            //客户端连接服务端事件	 SelectionKey.OP_CONNECT(8)
            //读事件 SelectionKey.OP_READ(1)
            //写事件	SelectionKey.OP_WRITE(4)
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            started = true;
            System.out.println("server started……");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void stop() {
        started = false;
    }

    @Override
    public void run() {
        while (started) {
            try {
                System.out.println("before selector");
                selector.select();//blocked
                System.out.println("after selector,times:" + count);
                count++;
                Set<SelectionKey> keys = selector.selectedKeys();
                System.out.println("keys:" + keys.size());
                for (SelectionKey key : keys) {
                    handleInput(key);
                }
                keys.clear();//清除处理过的事件
                System.out.println("one selector over");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleInput(SelectionKey key) {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                /*
                 * 客户端请求连接事件
                 * serversocket为该客户端建立socket连接，将此socket注册READ事件，监听客户端输入
                 * READ事件：当客户端发来数据，并已被服务器控制线程正确读取时，触发该事件
                 */
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                try {
                    SocketChannel socket = ssc.accept();
                    socket.configureBlocking(false);
                    socket.register(selector, SelectionKey.OP_READ);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (key.isReadable()) {
                /*
                 * READ事件，收到客户端发送数据，读取数据后继续注册监听客户端
                 */
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                try {
                    int readBytes = socketChannel.read(byteBuffer);
                    if (readBytes > 0) {
                        byteBuffer.flip();
                        byte[] bytes = new byte[byteBuffer.remaining()];
                        byteBuffer.get(bytes);
                        String receive = new String(bytes, "UTF-8");
                        System.out.println("服务端收到消息：" + receive);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        socketChannelList.add(socketChannel);
                        doWrite(socketChannel, receive);
                    } else if (readBytes == 0) {
                        //没有读取到自己，忽略
                    } else if (readBytes < 0) {//链路已经关闭
                        key.cancel();
                        socketChannel.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String response) throws ClosedChannelException {
        Socket s = socketChannel.socket();
        String name = "[" + s.getInetAddress().toString().substring(1) + ":" + Integer.toHexString(socketChannel.hashCode()) + "]";
        System.out.println("name:" + name);
        clientsMap.put(name, socketChannel);
        System.out.println(clientsMap.size());
        byte[] bytes = response.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        try {
            socketChannel.write(writeBuffer);
//            for (Map.Entry<String, SocketChannel> entry : clientsMap.entrySet()) {
//                SocketChannel temp = entry.getValue();
//                //输出到通道
//                temp.write(writeBuffer);
//            }
//
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
