package com.example.txredission.exception;

import org.springframework.transaction.TransactionException;

/**
 * 事务锁异常
 *
 * @author wenx
 * @date 2020-12-03
 */
public class TxException extends TransactionException {

    public TxException(String msg) {
        super(msg);
    }

    public TxException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
