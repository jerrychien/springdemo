package com.jerry.spider;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jerrychien on 2016/12/6.
 */
public class SpiderTest {

    private static final String DOWN_DIR = "/Users/jerrychien/Documents/spider/";

    public static void main(String[] args) {
        downLoadFileWithUrl("https://www.baidu.com/img/bd_logo1.png");
    }

    public static void downLoadFileWithUrl(String url) {
        String postFix = ".png";
        if (StringUtils.isNotBlank(url)) {
            postFix = url.substring(url.lastIndexOf("."));
        }
        System.out.println("postFix:" + postFix);
        try {
            URL url1 = new URL(url);
            URLConnection urlConnection = url1.openConnection();
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
}
