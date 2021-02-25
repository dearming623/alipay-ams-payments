package com.ming.alipay.ams.entity;

import lombok.Data;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/29/2020 3:51 PM
 */
@Data
public class  Address {
    private String region = "US";
    private String state = "California";
    private String city = "San Mateo";
    private String address1 = "400 El Camino Real";
    private String address2 = "suit 107";
    private String zipCode = "95014";
}
