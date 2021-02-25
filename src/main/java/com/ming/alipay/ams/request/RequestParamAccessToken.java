package com.ming.alipay.ams.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 6:55 PM
 */
@Data
@Builder
public class RequestParamAccessToken implements AMSRequestParam {
    private String authCode;
    @Builder.Default
    private String customerBelongsTo = "GCASH";
    @Builder.Default
    private String grantType = "AUTHORIZATION_CODE";
}
