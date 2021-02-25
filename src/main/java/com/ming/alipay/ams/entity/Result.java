package com.ming.alipay.ams.entity;

import com.ming.alipay.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 6:31 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String resultCode;
    private String resultStatus;
    private String resultMessage;

    public Result(ResultCode resultCode) {
        this.resultCode = resultCode.getCode();
        this.resultMessage = resultCode.getMsg();
        this.resultStatus = StringUtils.EMPTY;
    }
}
