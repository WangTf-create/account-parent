package com.citi.mvnbook.account.captcha.service.impl;

import com.citi.mvnbook.account.captcha.exception.AccountCaptchaException;
import com.citi.mvnbook.account.captcha.service.AccountCaptchaService;
import com.citi.mvnbook.account.captcha.util.RandomGenerator;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author wangtongfa
 * @date 2022/11/20 20:34
 */
@Service
public class AccountCaptchaServiceImpl implements AccountCaptchaService, InitializingBean {

    private final Map<String, String> captchaMap = Maps.newHashMap();

    private List<String> predefinedTexts;

    private int textCount = 0;

    @Override
    public String generateCaptchaKey() throws AccountCaptchaException {
        String key = RandomGenerator.getRandom();
        String text = getCaptchaText();
        captchaMap.put(key, text);
        return key;
    }

    @Override
    public byte[] generateCaptchaImage(String captchaKey) throws AccountCaptchaException {
        String value = captchaMap.get(captchaKey);
        if (StringUtils.isEmpty(value)) {
            throw new AccountCaptchaException("Captcha Key' " + captchaKey + " ' not found");
        }
        BufferedImage bufferedImage = producer.createImage(value);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (IOException e) {
            throw new AccountCaptchaException("Failed to write captcha stream!", e);
        }
        return outputStream.toByteArray();
    }

    @Override
    public boolean validateCaptcha(String captchaKey, String captchaValue) throws AccountCaptchaException {
        String text = captchaMap.get(captchaKey);
        if(text==null){
            throw new AccountCaptchaException("Captcha Key' " + captchaKey + " ' not found");
        }
        if(text.equals(captchaValue)){
            captchaMap.remove(captchaKey);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getPredefinedTexts() throws AccountCaptchaException {
        return this.predefinedTexts;
    }

    @Override
    public void setPredefinedTexts(List<String> predefinedTexts) throws AccountCaptchaException {
        this.predefinedTexts = predefinedTexts;
    }

    private DefaultKaptcha producer;

    @Override
    public void afterPropertiesSet() throws Exception {
        producer = new DefaultKaptcha();
        producer.setConfig(new Config(new Properties()));
    }

    private String getCaptchaText() {
        if (predefinedTexts != null && !predefinedTexts.isEmpty()) {
            String text = predefinedTexts.get(textCount);
            textCount = (textCount + 1) % predefinedTexts.size();
            return text;
        } else {
            return producer.createText();
        }
    }
}
