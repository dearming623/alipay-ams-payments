package com.ming.alipay.ams.entity;

import lombok.Data;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/29/2020 11:06 AM
 */
@Data
public class Order {
    private String referenceOrderId;
    private String orderDescription;
    private Amount orderAmount;
    private Merchant merchant;
    private Env env;
    private Goods goods;
    private Shipping shipping;
    private Buyer buyer;
    private String extendInfo;
}
