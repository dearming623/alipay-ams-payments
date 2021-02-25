package com.ming.alipay.ams.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 8/1/2020 1:29 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentFactor {
    @Builder.Default
    private String inStorePaymentScenario = "PaymentCode";
}
