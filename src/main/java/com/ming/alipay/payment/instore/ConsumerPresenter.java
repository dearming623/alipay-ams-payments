package com.ming.alipay.payment.instore;

import com.alibaba.fastjson.JSON;
import com.ming.alipay.api.AMSHeader;
import com.ming.alipay.api.RestfulPath;
import com.ming.alipay.api.Signature;
import com.ming.alipay.ams.entity.*;
import com.ming.alipay.ams.request.AMSRequestParam;
import com.ming.alipay.ams.request.RequestParamConsult;
import com.ming.alipay.ams.response.AMSObjectCallback;
import com.ming.alipay.ams.response.AMSResponse;
import com.ming.alipay.config.AMSConfiguration;
import com.ming.alipay.pojo.dto.*;
import com.ming.alipay.util.DateUtil;
import com.ming.alipay.util.RestExample;
import com.ming.alipay.util.SignatureUtil;

import java.util.*;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 5:58 PM
 * @Link https://global.alipay.com/docs/ac/ams_upm/introduction
 * Consumer presented Mode Payment
 * 消费者呈现支付方式
 */
public class ConsumerPresenter {

    public static void consult(String authState, AMSConfiguration configuration, AMSObjectCallback<AMSResponse> callback) {

        ConsultDTO requestParamConsult = ConsultDTO.builder()
                .customerBelongsTo("ALIPAY_CN")
                .authRedirectUrl("https://www.merchant.com/authenticationResult")
//                .scopes(Arrays.asList("AGREEMENT_PAY", "BASE_USER_INFO"))
                .scopes(Arrays.asList("AGREEMENT_PAY"))
                .authState(authState)
                .terminalType("APP")
                .osType("IOS")
                .osVersion("11.0.2")
                .build();

        consult(authState, requestParamConsult, configuration, callback);
    }

    public static void consult(String authState,
                               ConsultDTO requestParamConsult,
                               AMSConfiguration configuration,
                               AMSObjectCallback<AMSResponse> callback) {
//        String agentToken = "i23ZOAZRnDFzozDxyLwfvxg4LIuJNGT8zad6RMuwED0007C1";
        RestExample.<AMSResponse>builder()
                .sdkConfiguration(configuration)
                .restfulPath(RestfulPath.API_CONSULT)
                .requestBody(JSON.toJSONString(requestParamConsult))
                .requestTime(DateUtil.getISODateTimeStrNow())
                .build()
                .post(AMSResponse.class, callback);
    }


    public static void pay(String agentToken,
                           String paymentRequestId,
                           Order order,
                           String buyerCode, AMSConfiguration configuration, AMSObjectCallback<Payment> callback
    ) {
        PayPaymentDTO param = PayPaymentDTO.builder()
                .paymentRequestId(paymentRequestId)
                .order(order)
                .paymentMethod(new PaymentMethod("CONNECT_WALLET", buyerCode))
                .paymentAmount(order.getOrderAmount().clone())
                .paymentFactor(new PaymentFactor())
                .build();

        pay(agentToken, param, configuration, callback);

    }

    public static void pay(String agentToken, PayPaymentDTO param, AMSConfiguration configuration, AMSObjectCallback<Payment> callback
    ) {
        RestExample.<Payment>builder()
                .sdkConfiguration(configuration)
                .restfulPath(RestfulPath.API_PAY)
                .agentToken(agentToken)
                .requestBody(JSON.toJSONString(param))
                .requestTime(DateUtil.getISODateTimeStrNow())
                .build()
                .post(Payment.class, callback);
    }


    public static void cancel(String agentToken, String paymentId, AMSConfiguration configuration, AMSObjectCallback<CancelPayment> callback) {
        CancelPaymentDTO param = new CancelPaymentDTO();
        param.setPaymentId(paymentId);
        cancel(agentToken, param, configuration, callback);
    }

    public static void cancel(String agentToken, CancelPaymentDTO param, AMSConfiguration configuration, AMSObjectCallback<CancelPayment> callback) {
        RestExample.<CancelPayment>builder()
                .sdkConfiguration(configuration)
                .restfulPath(RestfulPath.API_CANCEL)
                .requestBody(JSON.toJSONString(param))
                .requestTime(DateUtil.getISODateTimeStrNow())
                .agentToken(agentToken)
                .build()
                .post(CancelPayment.class, callback);
    }

    public static void refund(String agentToken, String paymentId, String refundRequestId, Amount amount, AMSConfiguration config, AMSObjectCallback<RefundPayment> callback) {
        RefundPaymentDTO param = new RefundPaymentDTO();
        param.setPaymentId(paymentId);
        param.setRefundAmount(amount);
        param.setRefundRequestId(refundRequestId);
        refund(agentToken, param, config, callback);
    }

    public static void refund(String agentToken, RefundPaymentDTO param, AMSConfiguration config, AMSObjectCallback<RefundPayment> callback) {
        RestExample.<RefundPayment>builder()
                .sdkConfiguration(config)
                .restfulPath(RestfulPath.API_REFUND)
                .requestBody(JSON.toJSONString(param))
                .requestTime(DateUtil.getISODateTimeStrNow())
                .agentToken(agentToken)
                .build()
                .post(RefundPayment.class, callback);
    }


    public static void inquiry(String agentToken, String paymentId, AMSConfiguration configuration, AMSObjectCallback<InquiryPayment> callback) {
        InquiryPaymentDTO param = new InquiryPaymentDTO();
        param.setPaymentId(paymentId);
        inquiry(agentToken, param, configuration, callback);
    }

    public static void inquiry(String agentToken, InquiryPaymentDTO param, AMSConfiguration configuration, AMSObjectCallback<InquiryPayment> callback) {
        RestExample.<InquiryPayment>builder()
                .sdkConfiguration(configuration)
                .restfulPath(RestfulPath.API_INQUIRY)
                .requestBody(JSON.toJSONString(param))
                .requestTime(DateUtil.getISODateTimeStrNow())
                .agentToken(agentToken)
                .build()
                .post(InquiryPayment.class, callback);
    }

}
