package com.ming.alipay.pojo.dto;

import com.ming.alipay.ams.entity.Amount;
import com.ming.alipay.ams.entity.Order;
import com.ming.alipay.ams.entity.PaymentFactor;
import com.ming.alipay.ams.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 6:26 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayPaymentDTO {
    @Builder.Default
    private String productCode = "IN_STORE_PAYMENT";
    @Builder.Default
    private String paymentNotifyUrl = "http://xmock.inc.alipay.net/api/Ipay/globalSite/automtion/paymentNotify.htm";
    @Builder.Default
    private String paymentRequestId = "pay_1089760038715669_102775745075669";
    private PaymentFactor paymentFactor;
    private Order order;
    private Amount paymentAmount;
    private PaymentMethod paymentMethod;
}
