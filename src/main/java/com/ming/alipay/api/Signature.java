package com.ming.alipay.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 5:45 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Signature {

    @Builder.Default
    private String algorithm = "RSA256";
    @Builder.Default
    private String keyVersion = "1";
    private String signature;

//    public void parse(String signContent) {
//        algorithm = StringUtils.substringBetween(signContent, "algorithm=", ",");
//        keyVersion = StringUtils.substringBetween(signContent, "keyVersion=", ",");
//        signature = StringUtils.substringBetween(signContent, "signature=");
//    }

    @Override
    public String toString() {
        return String.format("algorithm=%s,keyVersion=%s,signature=%s", algorithm, keyVersion,
                signature);
    }
}
