package com.ming.alipay.util;

import com.ming.alipay.api.AMSHeader;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 5:33 PM
 */
public class AmsUtil {

    public static HashMap<String, String> convertHttpHeader(AMSHeader header) {
        HashMap<String, String> map = new HashMap<String, String>();
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
