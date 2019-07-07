import com.wywhdgg.api.model.OrderModel;
import com.wywhdgg.api.serivce.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/***
 *@author lenovo
 *@date 2019/7/1 7:10
 *@Description:
 *@version 1.0
 */
public class ConsumerTest {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"http://10.20.160.198/wiki/display/dubbo/consumer.xml"});
        context.start();
        OrderService orderService = (OrderService)context.getBean("demoService"); // 获取远程服务代理
        OrderModel result = orderService.getOrder("world"); // 执行远程方法
        System.out.println( result ); // 显示调用结果
    }
}
