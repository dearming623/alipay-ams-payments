package com.ming.alipay.ams.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 8/1/2020 1:28 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public   class PaymentMethod {
    @Builder.Default
    private String paymentMethodType = "CONNECT_WALLET";
    @Builder.Default
    private String paymentMethodId = "281006020000000000125733";
}
