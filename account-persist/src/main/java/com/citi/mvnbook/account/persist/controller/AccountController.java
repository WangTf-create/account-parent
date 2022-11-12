package com.citi.mvnbook.account.persist.controller;

import com.citi.mvnbook.account.persist.exceptions.AccountPersistException;
import com.citi.mvnbook.account.persist.service.AccountPersistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangtongfa
 * @date 2022/11/6 19:53
 */
@RestController
public class AccountController {

    @Resource
    private AccountPersistService accountPersistService;

    @PostMapping("/account-persist/findOne")
    public ResponseEntity<Object> findOne(@RequestParam(required = true) String id) {
        try {
            return ResponseEntity.ok(accountPersistService.readAccount(id));
        } catch (AccountPersistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

