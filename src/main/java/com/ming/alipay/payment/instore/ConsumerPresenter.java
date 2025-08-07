package com.ming.alipay.payment.instore;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
 * @Link <a href="https://global.alipay.com/docs/ac/ams_upm/introduction">https://global.alipay.com/docs/ac/ams_upm/introduction</a>
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
        RestExample.builder()
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
        RestExample.builder()
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
        RestExample.builder()
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
        RestExample.builder()
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
        RestExample.builder()
                .sdkConfiguration(configuration)
                .restfulPath(RestfulPath.API_INQUIRY)
                .requestBody(JSON.toJSONString(param))
                .requestTime(DateUtil.getISODateTimeStrNow())
                .agentToken(agentToken)
                .build()
                .post(InquiryPayment.class, callback);
    }

    public static void main(String[] args) {
        AMSConfiguration config = AMSConfiguration.builder()
                .clientId("SANDBOX_5Y08882Y3EDZ03753")
                .privateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCBJO3Tvj7VpW+4vMiaGJet6xUZnP40u6U+4q5kC2W3bsy1vQd6odUE0aqKgJ9G7G+qUdVYwYNsA5Srg1a/AE5u3ZAV75avvxpLPr5zg8+rlGBB+9EWAUysMjrkg13odIqn/fVwrXzoA7TKkUT2/4dYxb7YewV6GSC5Z100Q7DFrVE0AFJmmwDG0i3mmT5zSHSBSLQnCaoZAU8HLEKWVds9Rrb8OE8q297ca25L+po/P5A5k/z2pmXMp4mgslt1rIPvqTbxLMQkMDg4OkOjIjcl8IP2SLK930UM3ML+XrHXs+BdASh81y4hQsJecghQ2QQVHobzHYTQstGe9cNXup2rAgMBAAECggEALv/smfIT1hcmSLpT21j14rK6dB7hmPltkyxWQUywkQh+8elAjo3VA7L2eP+EeABWR0sO9QVAOhA3jA/5gvuuj8SMyp7uXTmUbSwBjfK7onsWaQ0nM/X3HHgPXNi+VXoMeEUBjSdmXN30bwBdc/LdJB6bbRegj6YTdcY7cmjsLZR3SE8P5Llnr3K8tnV/v/SrUe19KTCyW7fdVNqeUmiuGrupaE0/rkumY+XvYIYwMQ4HYoX+clKibUzTHrKgkpOMh1c9+QI5Yf9dypkQ5Y6BFecpHhrnDQLzM/QmdznKOCvRiCl9RgTIl72G45GEv5KW6xh2kZZPUDAo5TRqIpW4UQKBgQDSSgJwzqYZyxxvsyLBh2jxeTNofi8UgiC08bxhIWWeWb+P00NSosZQgGDdK/yS1v1ulchHp6wyofrTRTxsmFJC3tnQaCMUQL5Aqa5mSZpTNcYYOB+aXSUxOk6B0vWYJ2Puj1lxTcFazJkj1JF+z4RtkH8qPGEsceCZxb59Za7fQwKBgQCdN3CFfk/EGgFpOS5xnUTEUNtmL114mCF5My33MQHgvStstlF5us5lzaDaBUgifBoR65uy0KKAMIhHbEhQSeWq+6sC4ncx2wsv6vNsyyu0tQ4FNGi7NrS63avtelyqmXXu0ddFKJoK4XM0ltEso8Jc57KRUTMyNUn3/f7gdk2deQKBgA9VnEXzm8Mnv4QZsQAhyrJJH0mQYgiB0LbBJfaDQ/C/JKvtQOyGuCVopyeZrIpcqRBmVbt765+plivP0a8tkFoV/BYpcd1pNzZp6TDGTz2gHzjm5s8P2cV0NgNbidM1lCDyTRhpDh976fLl7lIr+cEvl0ZSLtfZ2gH8nH5yasUXAoGBAJEIVcswjRFRFEAnugqhlJCuLtgDlezsGuUeK6dAFIVovaiaQGtVw5XyrhKykKsPZVVmzsmU4nPzUaTh5Yv7v92OMWKF3IOnmJhp3Ipem0EnTXnLsVoTy4IfgL2hBd9zlnHsLvTj0cd717WJ9AmEQIdIT1jzWZFVy5j1Pa2ANXWxAoGALoTb3JlHQIhfm0s/WyWlzyyuWm4UFeEzBjo5qBzL49ZOQhAL7y8pFTN1UntXMVq6enS4N0ljaSlUvyRLkgKC1JsQKetS9cOcb2LsAlMklBhMvZUBYSw+cHZcdpHqMZEfT754hXz5Zqa+PXeFC22Af4yG8nlrMKDObwA8M6GayfM=")
//                .alipayPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuKQMW5WWdVCWvmzv37qIUImpep6tzYlujOTM3jsOdbGF1uiwkm0sJRuOrN5rT6LDUzqv/nxcytQd4N6HlIfMkV8XNpmLiKoEk6dwZJVQB+9TAANtz0RQgyiGxh+NLEnhvmwvMRXqJi5CmmDXMyugdogEbl7PlCXMtiiaEOwHufqPfGjrbhF+85jLMPbK3RKfgwr2uRJtr1NSmIPl+NHVeOnmVEwDMxxqKny7IPbVi7k67tRkCV9juPpzaFnIcTI8Uj/ZjbVIgBgeOR4yz6WUaanlPzvRMhPHHyFNvZh8c6TmnIODIPlYcw7t8NTREPn2Rg2qvEHF68Bigyxw/GEOuwIDAQAB")
                .gatewayUrl("https://open-na.alipay.com")
//                .gatewayUrl("https://open-sea.alipay.com")
                .build();

        String agentToken = "zI7ZHWL4PURWs61dpIyckR1i1dZYZdZtKGgmqCemR00000A1";

        //amount
        long amountInCents = 1000L;
        Currency currency = Currency.getInstance("USD"); //CNY	China Yuan Renminbi
        Amount amount = new Amount(currency, amountInCents);

        System.out.println("Please input a payment code of buyer：");
        Scanner sc = new Scanner(System.in);
        String buyerCode = sc.nextLine();
//        pay(agentToken,"MQ20200731STN001_" + System.currentTimeMillis(),buyerCode, amount);

        String paymentRequestId = "MQ20200731STN001_" + System.currentTimeMillis();
        String referenceOrderId = "0000000001";

        Order order = new Order();
        order.setOrderAmount(amount.clone());
        order.setOrderDescription("New White Lace Sleeveless");
        order.setReferenceOrderId(referenceOrderId);
        order.setMerchant(
                new Merchant("Some_Mer", "seller231117459", "7011",
                        new Store("Some_store", "store231117459", "7011")));


        ConsumerPresenter.pay(agentToken, paymentRequestId, order, buyerCode, config, new AMSObjectCallback<Payment>() {
            @Override
            public void onSuccess(Payment response) {
                System.out.println();
                System.out.println("============ Response body ============ ");
                System.out.println(JSON.toJSONString(response, SerializerFeature.PrettyFormat));
            }

            @Override
            public void onHeader(HashMap<String, String> headerMap) {
                System.out.println();
                System.out.println("============ Response header ============ ");
                headerMap.forEach((k, v) -> System.out.println("" + k + " : " + v));
            }

            @Override
            public void onFailure(int code, Exception e) {
                System.out.println();
                System.out.println("============ Response onFaileure ============ ");
                System.out.println("code:" + code + "  exception:" + e.getMessage());

            }
        });


        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
