package com.citi.mvnbook.account.captcha.util;

import com.google.common.collect.Sets;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * @author wangtongfa
 * @date 2022/11/20 21:49
 */
public class RandomGeneratorTest {

    @Test(groups = {"util","medium"})
    public void testGetRandom(){
        Set<@Nullable String> randoms = Sets.newHashSet();
        for (int i = 0; i < 100; i++) {
            String random = RandomGenerator.getRandom();

            Assert.assertFalse(randoms.contains(random));

            randoms.add(random);
        }

    }

}