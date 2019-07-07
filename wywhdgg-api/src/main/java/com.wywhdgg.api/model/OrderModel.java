package com.wywhdgg.api.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author: dongzhb
 * @date: 2019/6/27
 * @Description:
 */
@Data
public class OrderModel implements Serializable {
    private static final long serialVersionUID = -6552937349057902432L;
    /** 订单类型 */
    private int orderType;
    /** 用户ID */
    private String userId;
    /** 订单名称 */
    private String orderName;
    /** 订单编号 */
    private String orderNo;

    @Override
    public String toString() {
        return "OrderModel{" + "orderType=" + orderType + ", userId='" + userId + '\'' + ", orderName='" + orderName + '\'' + ", orderNo='" + orderNo + '\'' + '}';
    }
}
