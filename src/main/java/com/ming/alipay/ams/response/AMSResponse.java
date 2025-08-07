package com.ming.alipay.ams.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.ming.alipay.ams.entity.PSPCustomerInfo;
import com.ming.alipay.ams.entity.Result;
import com.ming.alipay.enums.ResultCode;
import com.ming.alipay.util.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 6:30 PM
 */
@Data
@NoArgsConstructor
@ToString
public class AMSResponse {

    //    @Expose(serialize = false)
    @JSONField(serialize = false)
    private Map<String, List<String>> header;

    private Result result;

    //    private String paymentRequestId;
//    private String paymentId;
//    private String cancelTime;
//
//    private Amount paymentAmount;
//    private Amount actualPaymentAmount;
//
//    private String paymentTime;
//    private String paymentCreateTime;
    private PSPCustomerInfo pspCustomerInfo;

    private String authUrl;


    /* token */
    private String accessToken;
    private String accessTokenExpiryTime;
    private String refreshToken;
    private String refreshTokenExpiryTime;

    private RedirectActionForm redirectActionForm;

    @JSONField(serialize = false)
    public boolean isSuccess() {
        if (result == null) {
            return false;
        }
        return StringUtils.equals("S", result.getResultCode());
    }

    public AMSResponse noResponse() {
        this.result = new Result(ResultCode.NO_RESPONSE);
        return this;
    }


    @Data
    public class RedirectActionForm {
        private String method = "GET";
        private String redirectUrl = "https://www.alipaywallet.com?paymentId=20190608114010800100188820200355883&merchantRedirectUrl=www.alipay.com";
    }
}
