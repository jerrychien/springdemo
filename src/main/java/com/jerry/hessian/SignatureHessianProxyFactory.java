package com.jerry.hessian;

import com.caucho.hessian.client.HessianProxyFactory;
import com.jerry.util.SecurityUtil;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jerrychien on 2017-02-22 11:35.
 */
public class SignatureHessianProxyFactory extends HessianProxyFactory {

    private String serverKey = "123456";

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        URLConnection conn = super.openConnection(url);
        String timestamp = String.valueOf(System.currentTimeMillis());
        conn.setRequestProperty("Signature-Sign", SecurityUtil.md5(serverKey + timestamp));
        conn.setRequestProperty("Signature-Timestamp", timestamp);
        return conn;
    }
}
