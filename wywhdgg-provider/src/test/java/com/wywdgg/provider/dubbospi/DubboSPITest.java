package com.wywdgg.provider.dubbospi;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

import com.wywhdgg.provider.service.Robot;
/**
 * @author: dongzhb
 * @date: 2019/6/25
 * @Description:
 */
public class DubboSPITest {

//    @Test
    public void sayHello() throws Exception {
        ExtensionLoader<Robot> extensionLoader =
            ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }
}
