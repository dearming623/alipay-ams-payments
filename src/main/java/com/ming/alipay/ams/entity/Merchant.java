package com.ming.alipay.ams.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/29/2020 11:07 AM
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {
    private String referenceMerchantId;
    private String merchantName;
    private String merchantMCC;
    private Store store;


}
