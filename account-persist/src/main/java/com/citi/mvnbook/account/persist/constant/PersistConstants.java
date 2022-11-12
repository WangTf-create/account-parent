package com.citi.mvnbook.account.persist.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author wangtongfa
 * @date 2022/11/6 18:43
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersistConstants {

    public static final String ACCOUNT_ROOT = "account-persist";

    public static final String ACCOUNTS_ELEMENT = "accounts";


    public static final String ACCOUNT_ELEMENT="account";

    public static final String ACCOUNT_ELEMENT_ID = "id";

    public static final String ACCOUNT_ELEMENT_NAME = "name";

    public static final String ACCOUNT_ELEMENT_PASSWORD = "password";

    public static final String ACCOUNT_ELEMENT_EMAIL = "email";

    public static final String ACCOUNT_ELEMENT_ACTIVATED = "activated";
}
