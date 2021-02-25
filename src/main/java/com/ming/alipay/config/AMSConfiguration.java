package com.ming.alipay.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 5:57 PM
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AMSConfiguration {
    private String clientId;
    private String privateKey;
    private String alipayPublicKey;
    private String gatewayUrl;

}
