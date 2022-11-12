package com.citi.mvnbook.account.persist.service;

import com.citi.mvnbook.account.persist.model.Account;
import com.citi.mvnbook.account.persist.service.impl.AccountPersistServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

/**
 * @author wangtongfa
 * @date 2022/11/6 20:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        AccountPersistService.class, AccountPersistServiceImpl.class
})
public class AccountPersistServiceTest {

    @Autowired
    private AccountPersistService accountPersistService;

    @Before
    public void prepare() throws IOException {

        File file = new File("target/test-classes/persist-data.xml");
        if (file.exists()) {
            file.delete();
        }
        Account account = new Account();
        account.setId("citi_wtf");
        account.setName("wtf");
        account.setPassword("this_should_be_encrypted");
        account.setActivated(false);
        account.setEmail("wtf@citi.com");
        accountPersistService.createAccount(account);

    }

    @Test
    public void readAccount() {
        Account account = accountPersistService.readAccount("citi_wtf");
        Assert.assertEquals(account.getEmail(), "wtf@citi.com");
    }
}