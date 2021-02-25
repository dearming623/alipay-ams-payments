package com.ming.alipay.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 8/1/2020 12:53 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InquiryPaymentDTO {
    private String paymentId;
    private String paymentRequestId;
}
