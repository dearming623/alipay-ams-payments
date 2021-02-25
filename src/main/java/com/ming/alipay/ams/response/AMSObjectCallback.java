package com.ming.alipay.ams.response;

/**
 * @author Ming
 * @wechat 147877305
 * @date 8/1/2020 1:10 PM
 */
public interface AMSObjectCallback<T> extends Callback {
    void onSuccess(T t);
}

