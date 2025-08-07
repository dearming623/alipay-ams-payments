package com.ming.alipay.util;

import org.apache.commons.codec.binary.Base64;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;


/**
 * @author Ming
 */
public class SignatureUtil {

    public static String sign(String requestUrl, String clientId, String requestTime,
                              String privateKey, String requestBody) {
        String content = String.format("POST %s\n%s.%s.%s", requestUrl, clientId, requestTime,
                requestBody);
        String signed = sign(privateKey, content);
        try {
            return URLEncoder.encode(signed, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String sign(String privateKey, String content) {
        try {
            Signature signature = java.security.Signature
                    .getInstance("SHA256withRSA");
            byte[] decode = Base64.decodeBase64(privateKey.getBytes(StandardCharsets.UTF_8));
            PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(
                    new PKCS8EncodedKeySpec(decode));
//            PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(
//                    new X509EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes("UTF-8"))));
            signature.initSign(priKey);
            signature.update(content.getBytes(StandardCharsets.UTF_8));
            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
