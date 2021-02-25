package com.ming.alipay.ams.entity;

import com.ming.alipay.ams.response.AMSResponse;
import lombok.*;

import java.util.List;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 6:30 PM
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Payment extends AMSResponse {
    private String paymentRequestId;
    public String paymentId;
    private String paymentTime;
    private String paymentCreateTime;
    protected Amount paymentAmount;
    private Amount actualPaymentAmount;
    private PaymentQuote paymentQuote;
    private List<Transaction> transactions;

    private String paymentResultCode;
    private String paymentResultMessage;
    private String paymentStatus;

//    @Override
//    public String toString() {
//        return "Payment{" +
//                "paymentRequestId='" + getPaymentRequestId() + '\'' +
//                ", paymentId='" + getPaymentId() + '\'' +
//                ", paymentTime='" + getPaymentTime() + '\'' +
//                ", paymentCreateTime='" + getPaymentCreateTime() + '\'' +
//                ", paymentAmount=" + getPaymentAmount() +
//                ", actualPaymentAmount=" + getActualPaymentAmount() +
//                ", paymentQuote=" + getPaymentQuote() +
//                ", transactions=" + getTransactions() +
//                '}';
//    }
}
