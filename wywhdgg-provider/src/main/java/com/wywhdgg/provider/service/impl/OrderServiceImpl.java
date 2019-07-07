package com.wywhdgg.provider.service.impl;

import com.wywhdgg.api.model.OrderModel;
import com.wywhdgg.api.serivce.OrderService;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OrderServiceImpl
 * 
 */
public class OrderServiceImpl implements OrderService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private AtomicInteger orderNo = new AtomicInteger(0);

	@Override
	public String createOrder(OrderModel order) {
		logger.info("创建订单，收到参数请求={}",order.toString());
		String newOrderNo = String.valueOf(orderNo.incrementAndGet());
		// 执行业务代码
		// 。。。。。
		try {
			Thread.sleep(2000L);//时间更改,可以模拟超时
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		order.setOrderNo(newOrderNo);
		logger.info("处理结果={}",order.toString());
		return newOrderNo;
	}


	@Override
	public OrderModel getOrder(String orderNo) {
		logger.debug("查询订单，收到参数："+orderNo);
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		OrderModel order = new OrderModel();
		order.setOrderName("交易订单");
		order.setOrderType(2);
		order.setUserId("12307");
		order.setOrderNo(orderNo);
		logger.debug("查询订单，返回结果："+order);
		return order;
	}

}

