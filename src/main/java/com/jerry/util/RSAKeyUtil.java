package com.jerry.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSAKeyUtil
 *
 * @author jerrychien
 * @create 2016-08-13 23:32
 */
public class RSAKeyUtil {

    public static final String PUBLIC_KEY = "RSAPublicKey";

    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 数字签名   MD5withRSA
     */
    public static final String SIGN_ALGORITHM_MD5WITHRSA = "MD5withRSA";

    /**
     * 数字签名   SHA1WithRSA
     */
    public static final String SIGN_ALGORITHM_SHA1WITHRSA = "SHA1WithRSA";

    /**
     * 数字签名   SHA1WithDSA
     */
    public static final String SIGN_ALGORITHM_SHA1WITHDSA = "SHA1WithDSA";

    public static final int HEX_ENCODE = 0;

    public static final int BASE64_ENCODE = 1;

    /**
     * 生成RSA秘钥对
     *
     * @param encodeMode
     * @return
     */
    public static Map<String, String> init(int encodeMode) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, String> keyMap = new HashMap<String, String>(2);
            String rsaPublicKeyStr = null;
            String rsaPrivateKeyStr = null;
            if (encodeMode == HEX_ENCODE) {
                rsaPublicKeyStr = SecurityUtil.bytesToHexString(rsaPublicKey.getEncoded());
                rsaPrivateKeyStr = SecurityUtil.bytesToHexString(rsaPrivateKey.getEncoded());
            } else if (encodeMode == BASE64_ENCODE) {
                rsaPublicKeyStr = Base64.encodeBase64String(rsaPublicKey.getEncoded());
                rsaPrivateKeyStr = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
            }
            keyMap.put(PUBLIC_KEY, rsaPublicKeyStr);
            keyMap.put(PRIVATE_KEY, rsaPrivateKeyStr);
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RSAPublicKey loadRSAPublicKey(String rsaPublicKeyStr, int encodeMode) {
        byte[] bytes = null;
        if (encodeMode == HEX_ENCODE) {
            bytes = SecurityUtil.hexStringToBytes(rsaPublicKeyStr);
        } else if (encodeMode == BASE64_ENCODE) {
            bytes = Base64.decodeBase64(rsaPublicKeyStr);
        }
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RSAPrivateKey loadRSAPrivateKey(String rsaPrivateStr, int encodeMode) {
        byte[] bytes = null;
        if (encodeMode == HEX_ENCODE) {
            bytes = SecurityUtil.hexStringToBytes(rsaPrivateStr);
        } else if (encodeMode == BASE64_ENCODE) {
            bytes = Base64.decodeBase64(rsaPrivateStr);
        }
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA数字签名
     *
     * @param content
     * @param privateKeyStr
     * @param signAlgorithm
     * @return
     */
    public static String RSASign(String content, String privateKeyStr, String signAlgorithm) {
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initSign(loadRSAPrivateKey(privateKeyStr, RSAKeyUtil.BASE64_ENCODE));
            signature.update(content.getBytes("UTF-8"));
            byte[] bytes = signature.sign();
            return Base64.encodeBase64String(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA验证数字签名
     *
     * @param content
     * @param encodedContent
     * @param publicKeyStr
     * @param signAlgorithm
     * @return
     */
    public static boolean RSASignVertify(String content, String encodedContent, String publicKeyStr, String signAlgorithm) {
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initVerify(loadRSAPublicKey(publicKeyStr, RSAKeyUtil.BASE64_ENCODE));
            signature.update(content.getBytes("UTF-8"));
            return signature.verify(Base64.decodeBase64(encodedContent));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        Map<String, String> keyMap = RSAKeyUtil.init(RSAKeyUtil.BASE64_ENCODE);
        System.out.println(keyMap);
        String publicKeyStr = keyMap.get(RSAKeyUtil.PUBLIC_KEY);
        String privateKeyStr = keyMap.get(RSAKeyUtil.PRIVATE_KEY);
        System.out.println("publicKeyStr:" + publicKeyStr);
        System.out.println("privateKeyStr:" + privateKeyStr);
        RSAPublicKey publicKey = RSAKeyUtil.loadRSAPublicKey(publicKeyStr, RSAKeyUtil.BASE64_ENCODE);
        RSAPrivateKey privateKey = RSAKeyUtil.loadRSAPrivateKey(privateKeyStr, RSAKeyUtil.BASE64_ENCODE);
        String org = Base64.encodeBase64String(publicKey.getEncoded());
        String orgpri = Base64.encodeBase64String(privateKey.getEncoded());
        if (org.equals(publicKeyStr)) {
            System.out.println("fuck right!!!");
        }
        if (orgpri.equals(privateKeyStr)) {
            System.out.println("fuck2222 right!!!");
        }
        String orgContent = "我是中国人";
        String signResult = RSAKeyUtil.RSASign(orgContent, privateKeyStr, RSAKeyUtil.SIGN_ALGORITHM_MD5WITHRSA);
        System.out.println(signResult);
        boolean vertifyResult = RSAKeyUtil.RSASignVertify(orgContent, signResult, publicKeyStr, RSAKeyUtil.SIGN_ALGORITHM_MD5WITHRSA);
        System.out.println(vertifyResult);
    }
}
