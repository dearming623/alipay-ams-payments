package com.ming.alipay.enums;


/**
 * @author Ming
 * @wechat 147877305
 * @date 7/29/2020 12:33 PM
 */

public enum ResultCode {

    NO_RESPONSE("300000", "No Response");

    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
