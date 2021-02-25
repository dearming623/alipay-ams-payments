package com.ming.alipay.ams.response;

import java.util.HashMap;

/**
 * @author Ming
 * @wechat 147877305
 * @date 8/1/2020 1:08 PM
 */
public interface Callback {
    void onHeader(HashMap<String, String> headerMap);

    void onFaileure(int code, Exception e);
}
