package com.wywdgg.provider.javaspi;

import com.wywhdgg.provider.service.Robot;
import java.util.ServiceLoader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: dongzhb
 * @date: 2019/6/25
 * @Description:
 */
public class JavaSPITest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void sayHello() throws Exception {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("serviceLoader="+serviceLoader);
        logger.info("Java SPI ......");
        serviceLoader.forEach(Robot::sayHello);
    }
}
