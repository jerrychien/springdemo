package com.jerry.util;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jerrychien on 2016/12/7.
 */
public class HttpClientUtil {

    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * hc3.x和4.x的早期版本,提供了PoolingClientConnectionManager,DefaultHttpClient等类来实现http连接池,
     * 但这些类在4.3.x版本之后大部分就已经过时,本文使用4.5.x提供的最新的PoolingHttpClientConnectionManager
     * 等类进行http连接池的实现.
     */
    private static PoolingHttpClientConnectionManager connManager;
    private static CloseableHttpClient httpClient;
    private static RequestConfig requestConfig;
    private static final int MAX_CONNECT = 400;
    private static final int MAX_PRE_ROUTE = 300;
    private static final Map<String, String> defaultHeader;

    static {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        connManager = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加到400
        connManager.setMaxTotal(MAX_CONNECT);
        // 将每个路由基础的连接增加到20
        connManager.setDefaultMaxPerRoute(MAX_PRE_ROUTE);
        // Create socket configuration
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoKeepAlive(true)
                .setSoReuseAddress(true).build();
        connManager.setDefaultSocketConfig(socketConfig);
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.BEST_MATCH)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();
        requestConfig = RequestConfig.copy(defaultRequestConfig)
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        httpClient = HttpClients.custom().setConnectionManager(connManager).setRetryHandler(new DefaultHttpRequestRetryHandler()).build();
        defaultHeader = new HashMap<>();
        defaultHeader.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36");
        defaultHeader.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        defaultHeader.put("Accept-Encoding", "gzip, deflate, sdch");
        defaultHeader.put("Accept-Language", "zh-CN,zh;q=0.8");
        //这个需要改变
        defaultHeader.put("Cookie", "prov=d6aeb86a-ae03-2836-79e4-6f5203c38f1d; __qca=P0-779883152-1481079011901; _ga=GA1.2.1920497864.1481079015; _gat=1; _gat_pageData=1");
    }

    public static String getHttpGetResult(String url, Map<String, String> headers) {
        HttpGet httpGet = new HttpGet(url);
        handleHeader(httpGet, headers);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                if (response != null) {
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        HttpEntity entity = response.getEntity();
                        return EntityUtils.toString(entity, Consts.UTF_8);
                    } else {
                        httpGet.abort();
                    }
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
        return null;
    }

    /**
     * 通用的设置header的逻辑
     *
     * @param httpRequestBase
     * @param headers
     */
    private static void handleHeader(HttpRequestBase httpRequestBase, Map<String, String> headers) {
        httpRequestBase.setConfig(requestConfig);
        if (headers == null) {
            headers = new HashMap<>(defaultHeader);
        } else {
            headers.putAll(defaultHeader);
        }
        Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            httpRequestBase.setHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 下载并且存储文件
     *
     * @param url
     * @param headers
     * @param fileDir
     * @param title
     */
    public static void downLoadFile(String url, Map<String, String> headers, String fileDir, String title) {
        Assert.notNull(fileDir, "存储文件不能为空");
        File file = new File(fileDir);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HttpGet httpGet = new HttpGet(url);
        handleHeader(httpGet, headers);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                if (response != null) {
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        HttpEntity entity = response.getEntity();
                        InputStream inputStream = entity.getContent();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        String outFileName = fileDir + File.separator + getFileName(url, title);
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(outFileName));
                        byte bytes[] = new byte[1024];
                        int count;
                        while ((count = inputStream.read(bytes)) != -1) {
                            byteArrayOutputStream.write(bytes, 0, count);
                        }
                        byte[] fileBytes = byteArrayOutputStream.toByteArray();
                        logger.info("\nfileName :" + outFileName + "\nfile bytes :" + fileBytes.length);
                        fileOutputStream.write(fileBytes);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } else {
                        httpGet.abort();
                    }
                }
            } catch (Exception e) {
                logger.error("error", e);
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
    }

    private static String getFileName(String url, String title) {
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
        return (title == null ? "" : title) + "--" + simpleDateFormat.format(new Date()) + postFix;
    }

    public static void main(String[] args) {
        String result = HttpClientUtil.getHttpGetResult("https://www.baidu.com", null);
        System.out.println(result);
        HttpClientUtil.downLoadFile("https://www.baidu.com/img/bd_logo1.png?as=xd", null, "/Users/jerrychien/Documents/spider", "BaiduLogo");
    }

}
