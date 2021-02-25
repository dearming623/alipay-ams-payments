package com.ming.alipay.pojo.dto;

import com.ming.alipay.ams.entity.Amount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 8/1/2020 1:02 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefundPaymentDTO {
    private String paymentId;
    private String refundRequestId;
    private Amount refundAmount;

    private String referenceRefundId;
    private String refundReason;
    private boolean isAsyncRefund;
}
