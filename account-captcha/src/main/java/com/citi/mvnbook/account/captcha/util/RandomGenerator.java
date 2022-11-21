package com.citi.mvnbook.account.captcha.util;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author wangtongfa
 * @date 2022/11/20 20:11
 */
public class RandomGenerator {
    private static final String[] RANGE = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    };


    public static synchronized String getRandomString() {
        Random random = new Random();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(RANGE[random.nextInt(RANGE.length)]);
        }
        return sb.toString();

    }

    public static synchronized String getRandom() {
        Random random = new Random();
        return IntStream.rangeClosed(0, 8).mapToObj(i -> RANGE[random.nextInt(RANGE.length)]).
                reduce(new StringBuilder(), StringBuilder::append, StringBuilder::append).toString();
    }

}
