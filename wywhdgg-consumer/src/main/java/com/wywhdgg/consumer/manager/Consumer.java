package com.wywhdgg.consumer.manager;

import com.wywhdgg.api.model.OrderModel;
import com.wywhdgg.api.serivce.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 客户端启动方式示例
 * Consumer
 */
public class Consumer {
	/**
	 * 通过spring xml启动方式示例
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
		context.start();
		OrderService orderService = (OrderService) context.getBean("orderService"); // 获取远程服务代理
		OrderModel order = new OrderModel();
		order.setOrderName("充值订单");
		order.setOrderType(1);
		order.setUserId("12306");
		order.setOrderNo("");
		String orderNo = orderService.createOrder(order); // 执行远程方法
		System.out.println("获取订单号："+orderNo); // 显示调用结果
		System.out.println("----------------------");
//		OrderModel orderModel = orderService.getOrder("");
//		System.out.println("order detail"+orderModel);
		System.out.println(orderService);
		System.in.read();
		context.close();
	}

	/**
	 *
	 * 07:21:07,379 DEBUG OrderServiceMock:19 - 这是一个mock实现：OrderModel{orderType=1, userId='12306', orderName='充值订单', orderNo=''}
	 * 07:21:07,580 DEBUG OrderServiceMock:30 - mock处理结果为：OrderModel{orderType=1, userId='12306', orderName='充值订单', orderNo='mock-1'}
	 * mock-1
	 */

}
