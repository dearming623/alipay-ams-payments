package com.ming.alipay.ams.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/31/2020 11:46 AM
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CancelPayment extends Payment {
    private String cancelTime;
}
