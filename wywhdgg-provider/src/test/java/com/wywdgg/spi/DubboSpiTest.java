package com.wywdgg.spi;

import com.wywhdgg.provider.spi.Robot;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;


/**
 * DubboSPITest
 * 
 */
public class DubboSpiTest {
	
	@Test
    public void sayHello() throws Exception {
        ExtensionLoader<Robot> extensionLoader =
            ExtensionLoader.getExtensionLoader(Robot.class);
        
        Robot defautRobot = extensionLoader.getDefaultExtension();
        defautRobot.sayHello();
        
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }
}

