package com.wywhdgg.api.serivce;

import java.util.concurrent.CompletableFuture;

/**
 * @author: dongzhb
 * @date: 2019/6/27
 * @Description:
 */
public interface AsyncService {
	CompletableFuture<String> sayHello(String name);
}

