package com.jerry.spider;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.RandomStringUtils;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jerrychien on 2016/12/6.
 */
public class SpiderTest {

    private static final String DOWN_DIR = "/Users/jerrychien/Documents/spider/";

    public static void main(String[] args) {
        //downLoadFileWithUrl("https://www.baidu.com/img/bd_logo1.png");
        String content = getContent("http://cuisuqiang.iteye.com/blog/1726173");
        List<String> urls = getAllPicUrls(content);
        System.out.println(urls);
        for (String url : urls) {
            downLoadFileWithUrl(url);
        }
    }

    public static void downLoadFileWithUrl(String url) {
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
            urlConnection.setRequestProperty("Cookie", "_javaeye_cookie_id_=1481006905866674; _javaeye3_session_=BAh7BjoPc2Vzc2lvbl9pZCIlM2JmNTJlYjBkMDFjZDNhOGY3MjI1N2Q0ODg1YThhOWQ%3D--4709175477c6aa258dcbd969621e17d9e0913539; Hm_lvt_e19a8b00cf63f716d774540875007664=1481007219,1481018157; Hm_lpvt_e19a8b00cf63f716d774540875007664=1481018157; dc_tos=ohrcyc; dc_session_id=1481018196725");
            urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            InputStream inputStream = urlConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int count;
            while ((count = inputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, count);
            }
            byteArrayOutputStream.flush();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(DOWN_DIR + getFileName() + postFix));
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

    public static String getFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return simpleDateFormat.format(new Date()) + "--" + RandomStringUtils.randomNumeric(4);
    }

    public static String getContent(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
            urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            urlConnection.setRequestProperty("Cookie", "_javaeye_cookie_id_=1481006905866674; _javaeye3_session_=BAh7BjoPc2Vzc2lvbl9pZCIlM2JmNTJlYjBkMDFjZDNhOGY3MjI1N2Q0ODg1YThhOWQ%3D--4709175477c6aa258dcbd969621e17d9e0913539; Hm_lvt_e19a8b00cf63f716d774540875007664=1481007219,1481018157; Hm_lpvt_e19a8b00cf63f716d774540875007664=1481018157; dc_tos=ohrcyc; dc_session_id=1481018196725");
            urlConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
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
