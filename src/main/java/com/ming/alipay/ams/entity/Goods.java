package com.ming.alipay.ams.entity;

import lombok.Data;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/29/2020 3:38 PM
 */
@Data
public class Goods {
    private String referenceGoodsId = "102775745075669";
    private String goodsName = "xxx goods";
    private Amount goodsUnitAmount;
    private String goodsQuantity = "1";
}
