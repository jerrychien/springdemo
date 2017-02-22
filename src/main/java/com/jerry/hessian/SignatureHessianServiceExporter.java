package com.jerry.hessian;

import com.jerry.util.SecurityUtil;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jerrychien on 2017-02-22 11:34.
 */
public class SignatureHessianServiceExporter extends HessianServiceExporter {

    private String serverKey = "123456";

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sign = ServletRequestUtils.getStringParameter(request, "Signature-Sign", null);
        String timestamp = ServletRequestUtils.getStringParameter(request, "Signature-Timestamp", null);
        if (sign == null || timestamp == null || !sign.equalsIgnoreCase(SecurityUtil.md5(serverKey + timestamp))) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        super.handleRequest(request, response);
    }

}
