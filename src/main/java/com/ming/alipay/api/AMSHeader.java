package com.ming.alipay.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 5:31 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AMSHeader {
    //  "application/json; charset=UTF-8"
    private String contentType;
    private String clientId;
    private String agentToken;
    private String signature;
    private String requestTime;
    private String encrypt;
}
