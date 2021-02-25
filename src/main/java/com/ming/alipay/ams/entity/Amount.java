package com.ming.alipay.ams.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 6:33 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amount implements Cloneable{
    private String currency;
    private String value;

    public Amount(Currency currency, Long val) {
        this.currency  = currency.getCurrencyCode();
        this.value = val.toString();
    }

    @Override
    public Amount clone() {
        Amount amt = null;
        try{
            amt = (Amount)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return amt;
    }
}
