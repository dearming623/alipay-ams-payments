package com.ming.alipay.ams.entity;

import lombok.Data;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/29/2020 3:48 PM
 */
@Data
public class Buyer {
    private String referenceBuyerId = "tester@163.com";
    private Name buyerName;
    private String buyerPhoneNo = "+86 18888888888";
    private String buyerEmail = "tester@163.com";
}
