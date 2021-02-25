package com.ming.alipay.ams.entity;

import lombok.Data;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/29/2020 3:40 PM
 */
@Data
public class Shipping {
    private Name shippingName;
    private Address shippingAddress;
    private String shippingCarrier = "USPS";
    private String shippingPhoneNo = "9093749555";
}
