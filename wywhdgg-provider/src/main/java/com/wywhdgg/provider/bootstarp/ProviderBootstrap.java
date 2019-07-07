package com.wywhdgg.provider.bootstarp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 服务端启动入口
 * Provider
 */
public class ProviderBootstrap {
	/**
	 * 通过main方法启动服务，手动方式加载spring容器
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
		//ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider-config-center.xml");
		context.start();
		System.out.println("-------------生产者提供服务启动---------------");
		System.in.read(); // 按任意键退出
		context.close();
	}
}
