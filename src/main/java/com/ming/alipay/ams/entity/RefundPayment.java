package com.ming.alipay.ams.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/31/2020 11:21 AM
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RefundPayment extends Payment {
    private String refundRequestId;
    private Amount refundAmount;
    private String refundId;
}
