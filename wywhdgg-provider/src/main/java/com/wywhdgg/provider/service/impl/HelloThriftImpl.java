package com.wywhdgg.provider.service.impl;

import com.wywhdgg.api.thrift.HelloThrift;
import com.wywhdgg.api.thrift.HelloThrift.Iface;
import org.apache.thrift.TException;

/***
 *@author lenovo
 *@date 2019/7/7 18:39
 *@Description:
 *@version 1.0
 */
public class HelloThriftImpl implements Iface {
    @Override
    public String sayHello(String para) throws TException {
        return "hello "+para+", this is thrift.";
    }
}
