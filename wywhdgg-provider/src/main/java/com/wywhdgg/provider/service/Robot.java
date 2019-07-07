package com.wywhdgg.provider.service;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author: dongzhb
 * @date: 2019/6/25
 * @Description:
 */
@SPI("bumblebee")
public interface Robot {
    void sayHello();
}
