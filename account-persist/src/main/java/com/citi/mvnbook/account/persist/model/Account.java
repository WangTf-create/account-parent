package com.citi.mvnbook.account.persist.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wangtongfa
 * @date 2022/11/5 21:59
 */
@Getter
@Setter
public class Account {
    private String id;
    private String name;
    private String email;
    private String password;
    private boolean activated;
}
