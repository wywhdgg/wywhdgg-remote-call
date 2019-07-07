import com.wywhdgg.api.serivce.AsyncService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.rpc.RpcContext;


/**
 * AsyncServiceTest
 * 
 */
public class AsyncServiceTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void test() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer-async.xml");
		context.start();
		final AsyncService asyncService = (AsyncService) context.getBean("asyncService");
		logger.debug("消费者Key："+this.hashCode());
		RpcContext.getContext().setAttachment("consumer-key1", String.valueOf(this.hashCode()));
		CompletableFuture<String> future = asyncService.sayHello("dong");
		
		try {
			String result = future.get();
			logger.debug("异步方法返回结果："+result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		context.close();
	}
}

