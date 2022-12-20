package com.citi.mvnbook.account.exceptions;

import java.io.Serial;

/**
 * @author wangtongfa
 * @date 2022/12/4 21:03
 */
public class AccountServiceException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = -8272204940254215450L;

    public AccountServiceException() {
    }

    public AccountServiceException(String message) {
        super(message);
    }

    public AccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
