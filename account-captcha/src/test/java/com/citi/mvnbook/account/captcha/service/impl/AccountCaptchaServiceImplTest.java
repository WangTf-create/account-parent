package com.citi.mvnbook.account.captcha.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author wangtongfa
 * @date 2022/11/20 21:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        AccountCaptchaServiceImpl.class
})
public class AccountCaptchaServiceImplTest {

    @Resource
    private AccountCaptchaServiceImpl accountCaptchaService;


    @Test
    public void testGenerateCaptcha() throws IOException {
        String captchaKey = accountCaptchaService.generateCaptchaKey();
        Assert.assertNotNull(captchaKey);

        byte[] captchaImage = accountCaptchaService.generateCaptchaImage(captchaKey);
        Assert.assertTrue(captchaImage.length > 0);

        File image = new File("target/" + captchaKey + ".jpg");
        try (FileOutputStream out = new FileOutputStream(image)) {
            out.write(captchaImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(image.exists() && image.length() > 0);
        FileUtils.forceDelete(image);
    }

    @Test
    public void testValidateCaptchaCorrect() {
        List<String> predefinedTexts = Lists.newArrayList("123456", "abcdef");
        accountCaptchaService.setPredefinedTexts(predefinedTexts);

        String captchaKey = accountCaptchaService.generateCaptchaKey();
        accountCaptchaService.generateCaptchaImage(captchaKey);
        Assert.assertTrue(accountCaptchaService.validateCaptcha(captchaKey,"123456"));

        captchaKey = accountCaptchaService.generateCaptchaKey();
        accountCaptchaService.generateCaptchaImage(captchaKey);
        Assert.assertTrue(accountCaptchaService.validateCaptcha(captchaKey,"abcdef"));

    }

    @Test
    public void testValidateCaptchaIncorrect(){
        List<String> predefinedTexts = Lists.newArrayList("12345");
        accountCaptchaService.setPredefinedTexts(predefinedTexts);

        String captchaKey = accountCaptchaService.generateCaptchaKey();
        accountCaptchaService.generateCaptchaImage(captchaKey);
        Assert.assertFalse(accountCaptchaService.validateCaptcha(captchaKey,"123456"));
    }
}