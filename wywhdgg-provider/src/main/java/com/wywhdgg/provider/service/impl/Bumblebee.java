package com.wywhdgg.provider.service.impl;

import com.wywhdgg.provider.service.Robot;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: dongzhb
 * @date: 2019/6/25
 * @Description:
 */
@Slf4j
public class Bumblebee implements Robot {
    @Override
    public void sayHello() {
        log.info("OptimusPrime hello,word");
        System.out.println("Hello, I am Bumblebee Prime.");
    }
}
