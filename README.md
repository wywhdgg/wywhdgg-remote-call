#20190624-dubbo分布式rpc 
解决场景问题： 

1.暴露的服务接口粒度比较粗，如何进行读写场景分离？
  路由规则
2.查询接口返回列表，单笔，躲避查询场景如何分离？

3.在运行时需要对某个应用额外多提供几台服务，怎么做？

3.在运行时需要调整服务权重，怎么做？

4.运行时怎么调整服务的负载均衡测落？

5.在运行时如何不停止提供服务，又能发布服务?
   动态配置，禁用某台服务。
6.灰度服务调用,如何处理？


------hystrix   参考地址:https://blog.csdn.net/MrSpirit/article/details/80401588
<dependency>
	<groupId>com.netflix.hystrix</groupId>
	<artifactId>hystrix-core</artifactId>
	<version>1.5.9</version>
</dependency>


------------实现一个自定义序列化协议----------------------


线程模式：
1.网络IO线程
2.业务线程

Dubbo粘带连接与延迟连接
http://dubbo.apache.org/zh-cn/docs/user/demos/stickiness.html
粘滞连接用于有状态服务，尽可能让客户端总是向同一提供者发起调用，除非该提供者挂了，再连另一台。
粘滞连接将自动开启延迟连接，以减少长连接数。
<dubbo:reference id="xxxService" interface="com.xxx.XxxService" sticky="true" />
Dubbo 支持方法级别的粘滞连接，如果你想进行更细粒度的控制，还可以这样配置。
<dubbo:reference id="xxxService" interface="com.xxx.XxxService">
    <dubbo:mothod name="sayHello" sticky="true" />
</dubbo:reference>

延迟连接
延迟连接用于减少长连接数。当有调用发起时，再创建长连接。[1]
<dubbo:protocol name="dubbo" lazy="true" />
注意：该配置只对使用长连接的 dubbo 协议生效。