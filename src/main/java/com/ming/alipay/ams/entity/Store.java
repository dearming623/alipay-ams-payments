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
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    private String referenceStoreId;
    private String storeName;
    private String storeMcc;
}
