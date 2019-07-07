package com.wywhdgg.provider.service.spi;

import com.wywhdgg.provider.service.Robot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: dongzhb
 * @date: 2019/6/25
 * @Description:
 */
public class OptimusPrime implements Robot {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void sayHello() {
        logger.info("OptimusPrime hello,word");
    }
}
