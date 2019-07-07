package com.wywhdgg.api.serivce;

import com.wywhdgg.api.model.OrderModel;

/**
 * @author: dongzhb
 * @date: 2019/6/27
 * @Description: 订单服务接口
 */
public interface OrderService {
	/**
	 * 创建订单
	 * @param order
	 * @return
	 */
	public String createOrder(OrderModel order);
	
	/**
	 * 查询订单信息
	 * @param orderNo
	 * @return
	 */
	public OrderModel getOrder(String orderNo);
}

