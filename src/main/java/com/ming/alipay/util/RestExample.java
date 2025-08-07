package com.ming.alipay.util;

import com.alibaba.fastjson.JSON;
import com.ming.alipay.ams.request.AMSRequestParam;
import com.ming.alipay.ams.response.AMSObjectCallback;
import com.ming.alipay.api.AMSHeader;
import com.ming.alipay.api.Signature;
import com.ming.alipay.config.AMSConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 6:06 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestExample {

    private AMSConfiguration sdkConfiguration;
    private AMSHeader amsHeader;
    private String restfulPath;
    private AMSRequestParam amsRequestParam;
    private String requestBody;
    private String requestTime;
    private String agentToken;

    public <T> void post(Class<T> cls, AMSObjectCallback<T> callback) {
        String sign = SignatureUtil.sign(restfulPath,
                sdkConfiguration.getClientId(),
                requestTime,
                sdkConfiguration.getPrivateKey(),
                requestBody);

        Signature signature = Signature.builder()
                .signature(sign)
                .build();

        //生成header
        if (amsHeader == null) {
            amsHeader = AMSHeader.builder()
                    .contentType("application/json; charset=UTF-8")
                    .clientId(sdkConfiguration.getClientId())
                    .signature(signature.toString())
                    .requestTime(requestTime)
                    .agentToken(agentToken)
                    .build();
        }

        String url = sdkConfiguration.getGatewayUrl() + restfulPath;
        HashMap<String, String> httpHeader = convertHttpHeader(amsHeader);

        if (amsRequestParam != null) {
            this.requestBody = JSON.toJSONString(amsRequestParam);
        }

        HttpUtil.sendRequestAsync("POST", url, httpHeader, requestBody, cls, callback);
    }

    private HashMap<String, String> convertHttpHeader(AMSHeader header) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Client-Id", header.getClientId());
        map.put("Signature", header.getSignature());

        if (StringUtils.isNotEmpty(header.getEncrypt())) {
            map.put("Encrypt", header.getEncrypt());
        }

        map.put("Content-Type", header.getContentType());
        map.put("Request-Time", header.getRequestTime());

        if (StringUtils.isNotEmpty(header.getAgentToken())) {
            map.put("Agent-Token", header.getAgentToken());
        }
        return map;
    }

}
