package com.ming.alipay.ams.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/31/2020 4:42 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String transactionType;
    private String transactionStatus;
    private String transactionRequestId;
    private Amount transactionAmount;
    private String transactionId;
    private Result transactionResult;
}
