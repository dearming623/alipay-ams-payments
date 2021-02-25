package com.ming.alipay.ams.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 7:10 PM
 */
@Data
@Builder
public class RequestParamConsult implements AMSRequestParam {
    @Builder.Default
    private String customerBelongsTo = "ALIPAY_CN";
    private String authRedirectUrl;
    private List<String> scopes;
    private String authState;
    private String terminalType;
    private String osType;
    private String osVersion;
}
