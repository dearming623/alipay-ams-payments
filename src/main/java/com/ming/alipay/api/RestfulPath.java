package com.ming.alipay.api;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 6:08 PM
 */
public class RestfulPath {


    public static String AMS_API = "/ams/api";

    /* payments */
    public static String API_PAY = AMS_API + "/v1/payments/pay";
    public static String API_INQUIRY = AMS_API + "/v1/payments/inquiryPayment";
    public static String API_CANCEL = AMS_API + "/v1/payments/cancel";
    public static String API_REFUND = AMS_API + "/v1/payments/refund";

    /* authorizations */
    public static String API_CONSULT = AMS_API + "/v1/authorizations/consult";
    public static String API_ApplyToken = AMS_API + "/v1/authorizations/applyToken";
    public static String API_REVOKE = AMS_API + "/v1/authorizations/revoke";


}
