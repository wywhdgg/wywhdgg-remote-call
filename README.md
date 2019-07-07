#20190624-dubbo分布式rpc 
解决场景问题： 

1.暴露的服务接口粒度比较粗，如何进行读写场景分离？

2.查询接口返回列表，单笔，躲避查询场景如何分离？

3.在运行时需要对某个应用额外多提供几台服务，怎么做？

3.在运行时需要调整服务权重，怎么做？

4.运行时怎么调整服务的负载均衡测落？

5.在运行时如何不停止提供服务，又能发布服务?

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