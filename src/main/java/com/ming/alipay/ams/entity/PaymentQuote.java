package com.ming.alipay.ams.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/31/2020 12:36 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentQuote {
    private Double quotePrice;
    private String quoteCurrencyPair;
    private Boolean guaranteed;
}
