package com.wywhdgg.common.util;

import java.util.Random;

/**
 * @author: dongzhb
 * @date: 2019/6/26
 * @Description:
 */
public class SleepUtil {

    private static Random random = new Random();

    /**
     * 模拟休眠时间
     * **/
    public static void randomSleep() {
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
