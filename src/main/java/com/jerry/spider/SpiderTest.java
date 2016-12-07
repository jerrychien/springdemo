package com.jerry.spider;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jerrychien on 2016/12/6.
 */
public class SpiderTest {

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

    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        //downLoadFileWithUrl("https://www.baidu.com/img/bd_logo1.png");
        //List<String> picUrls = new ArrayList<>();
        List<String> startUrlList = new ArrayList<>();
        startUrlList.add(START_URL1);
        startUrlList.add(START_URL2);
        for (int i = 2; i <= 2; i++) {
            startUrlList.add(START_URL1 + "&page=" + i);
            startUrlList.add(START_URL2 + "&page=" + i);
        }
        System.out.println(startUrlList);
        for (String startUrl : startUrlList) {
            if (!startUrl.startsWith("http")) {
                startUrl = URL_HEADER + startUrl;
            }
            String content = getContent(startUrl);
            if (StringUtils.isNotBlank(content)) {
                Map<String, String> urlsMap = getMapByRegex(content, LIST_REGEX);
                System.out.println(urlsMap);
                Iterator<Map.Entry<String, String>> iterator = urlsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    String url = entry.getKey();
                    final String name = entry.getValue();
                    if (!url.startsWith("http")) {
                        url = URL_HEADER + url;
                    }
                    String detailContent = getContent(url);
                    if (StringUtils.isNotBlank(detailContent)) {
                        List<String> picUlrs = getByRegex(detailContent, PIC_REGEX);
                        System.out.println(picUlrs);
                        for (final String urrr : picUlrs) {
                            executorService.execute(new Runnable() {
                                @Override
                                public void run() {
                                    downLoadFileWithUrl(urrr, name);
                                }
                            });
                        }
                        //picUrls.addAll(picUlrs);
                    }
                }
            }
        }
        //System.out.println(picUrls.size());
        //System.out.println(picUrls);

    }

    public static void downLoadFileWithUrl(String url, String title) {
        String postFix = ".png";
        if (StringUtils.isNotBlank(url)) {
            int append = url.lastIndexOf("?");
            if (append != -1) {
                postFix = url.substring(url.lastIndexOf("."), append);
            } else {
                postFix = url.substring(url.lastIndexOf("."));
            }
        }
        System.out.println("postFix:" + postFix);
        try {
            URL url1 = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
            urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            urlConnection.setRequestProperty("Cookie", "__cfduid=d3519c9e3ad0036ed31a65686be2b38211481039339; aafaf_lastvisit=0%091481039339%09%2Fpw%2Fthread.php%3Ffid%3D15; aafaf_threadlog=%2C15%2C; a0888_pages=1; a0888_times=1");
            urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            InputStream inputStream = urlConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int count;
            while ((count = inputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, count);
            }
            byteArrayOutputStream.flush();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(DOWN_DIR + getFileName(title) + postFix));
            byte resultBytes[] = byteArrayOutputStream.toByteArray();
            fileOutputStream.write(resultBytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            System.out.println("download succ! total " + resultBytes.length + " bytes");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileName(String title) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
        return title + "--" + simpleDateFormat.format(new Date());
    }

    public static String getContent(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
            urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            urlConnection.setRequestProperty("Cookie", "__cfduid=d3519c9e3ad0036ed31a65686be2b38211481039339; aafaf_lastvisit=0%091481039339%09%2Fpw%2Fthread.php%3Ffid%3D15; aafaf_threadlog=%2C15%2C; a0888_pages=1; a0888_times=1");
            urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb.toString());
            reader.close();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getByRegex(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        List<String> urls = new ArrayList<>();
        while (matcher.find()) {
            urls.add(matcher.group(1));
        }
        return urls;
    }

    public static Map<String, String> getMapByRegex(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        Map<String, String> urls = new HashMap<>();
        while (matcher.find()) {
            urls.put(matcher.group(1), matcher.group(2));
        }
        return urls;
    }

    public static List<String> getAllPicUrls(String content) {
        String regex = "(http|https)://[a-zA-z0-9./-]+\\.(jpg|png)(\\?\\w+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        List<String> urls = new ArrayList<>();
        while (matcher.find()) {
            urls.add(matcher.group());
        }
        return urls;
    }
}
