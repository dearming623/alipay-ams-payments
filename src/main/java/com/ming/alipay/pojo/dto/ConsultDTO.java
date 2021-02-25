package com.ming.alipay.pojo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Ming
 * @wechat 147877305
 * @date 8/3/2020 2:41 PM
 */
@Data
@Builder
public class ConsultDTO {
    @Builder.Default
    private String customerBelongsTo = "ALIPAY_CN";
    private String authRedirectUrl;
    private List<String> scopes;
    private String authState;
    private String terminalType;
    private String osType;
    private String osVersion;
}
