package com.ming.alipay.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/29/2020 12:33 PM
 */

@Getter
@AllArgsConstructor
public enum ResultCode {

    NO_RESPONSE("300000", "No Response");

    private String code;
    private String msg;
}
