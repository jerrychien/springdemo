package com.jerry.spider;

import com.jerry.util.HttpClientUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jerrychien on 2016/12/7.
 */
public class SpiderEnhanceTest {

    private static ConcurrentLinkedQueue<PicUrlAndName> queue = new ConcurrentLinkedQueue<>();

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    private static final String DOWN_DIR = "/Users/jerrychien/Documents/spider/";

    //主帖子地址1
    private static final String START_URL1 = "http://1024.05ia.co/pw/thread.php?fid=15";

    //主帖子地址2
    private static final String START_URL2 = "http://1024.05ia.co/pw/thread.php?fid=49";

    //相对地址的header
    private static final String URL_HEADER = "http://1024.05ia.co/pw/";

    //主帖子总的条目
    private static final String LIST_REGEX = "<h3><a href=\"(htm_data[^>]+?)\"[^>]+?>([^<>]+?)</a>";

    //图片地址
    private static final String PIC_REGEX = "<img src=\"([^>]+?)\"[^>]+?border=\"0\"[^>]+?>";

    private static final Map<String, String> cookieHeader;

    static {
        cookieHeader = new HashMap<>();
        cookieHeader.put("Cookie", "__cfduid=d3519c9e3ad0036ed31a65686be2b38211481039339; aafaf_lastvisit=0%091481039339%09%2Fpw%2Fthread.php%3Ffid%3D15; aafaf_threadlog=%2C15%2C; a0888_pages=1; a0888_times=1");
    }

    static class PicUrlAndName {

        public String url;
        public String name;

        public PicUrlAndName(String url, String name) {
            this.url = url;
            this.name = name;
        }
    }

    public static void main(String[] args) {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final PicUrlAndName picUrlAndName = queue.poll();
                    if (picUrlAndName != null) {
                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                HttpClientUtil.downLoadFile(picUrlAndName.url, cookieHeader, DOWN_DIR, picUrlAndName.name);
                            }
                        });
                    }
                }
            }
        });

        List<String> startUrlList = new ArrayList<>();
        startUrlList.add(START_URL1);
        //startUrlList.add(START_URL2);
        for (int i = 2; i <= 5; i++) {
            startUrlList.add(START_URL1 + "&page=" + i);
            //startUrlList.add(START_URL2 + "&page=" + i);
        }
        System.out.println(startUrlList);
        for (String startUrl : startUrlList) {
            if (!startUrl.startsWith("http")) {
                startUrl = URL_HEADER + startUrl;
            }
            String content = HttpClientUtil.getHttpGetResult(startUrl, cookieHeader);
            if (StringUtils.isNotBlank(content)) {
                Map<String, String> urlsMap = SpiderTest.getMapByRegex(content, LIST_REGEX);
                System.out.println(urlsMap);
                Iterator<Map.Entry<String, String>> iterator = urlsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    String url = entry.getKey();
                    final String name = entry.getValue();
                    if (!url.startsWith("http")) {
                        url = URL_HEADER + url;
                    }
                    String detailContent = SpiderTest.getContent(url);
                    if (StringUtils.isNotBlank(detailContent)) {
                        List<String> picUlrs = SpiderTest.getByRegex(detailContent, PIC_REGEX);
                        System.out.println(picUlrs);
                        for (String urrr : picUlrs) {
                            queue.add(new PicUrlAndName(urrr, name));
                        }
                    }
                }
            }
        }
    }
}
