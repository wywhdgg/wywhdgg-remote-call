import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Random;

import org.I0Itec.zkclient.ZkClient;

import com.alibaba.fastjson.JSON;

/**
 * 分析dubbo客户端调用的过程
 * 协议头的处理
 *协议长度 16KB=128bit
 * Magic  16bits
 * Req/Req 1bit
 * way 1bit
 * event 1bit
 * serialization id 5bit
 * status 8bits
 * request id 64bits
 * data length 32bits
 *
 * */
public class DubboClient {
	
	public static void main(String[] args) throws Exception {
		// 1、 需要知道 有哪些接口可以被调用，通过zookeeper客户端去查询里面的信息
		ZkClient client = new ZkClient("127.0.0.1:2181");
		List<String> providers = client.getChildren("/dubbo/com.wywhdgg.api.serivce.OrderService/providers");

		// 2、 拿到了所有的服务提供者信息(ip+port)， 就可做 客户端负载均衡（策略：随机、轮询、或者固定访问第一台）
		int randomNum = new Random().nextInt(providers.size());
		System.out.println("未解码："+providers.get(randomNum));
		String providerUrl = URLDecoder.decode(providers.get(randomNum), "utf-8");
		System.out.println("解码："+providerUrl);
		String[] ipPort = providerUrl.split("/")[2].split(":");
		String host = ipPort[0];
		int port = Integer.valueOf(ipPort[1]);
		System.out.println("发现了一个服务提供者实例，信息如下：" + host + ":" + port);
		// 3、 dubbo代理对象，底层应该是发起一次网络请求， 然后把 服务提供者的返回值，作为 代理对象的返回值
		// 我还觉得： 客户端肯定在网络请求的数据中， 告诉服务提供者，我要调用 类名、方法名、参数
		// 网络调用（我直接用的jdk nio api，简单易懂。 dubbo 用到 netty）
		SocketChannel dubboClient = SocketChannel.open();
		dubboClient.connect(new InetSocketAddress(host, port));
		// 发送数据 - body段
		StringBuffer bodyString = new StringBuffer();
		// rpc协议的版本
		bodyString.append(JSON.toJSONString("2.0.1")).append("\r\n");
		// 接口地址
		bodyString.append(JSON.toJSONString("com.wywhdgg.api.serivce.OrderService")).append("\r\n");
		// 接口版本
		bodyString.append(JSON.toJSONString("0.0.0")).append("\r\n");
		// 具体方法名
		bodyString.append(JSON.toJSONString("getOrder")).append("\r\n");
		// 参数描述
		bodyString.append(JSON.toJSONString("Ljava/lang/String;")).append("\r\n");
		// 参数值
		bodyString.append(JSON.toJSONString("hello word!")).append("\r\n");
		// 附加参数(用于拓展dubbo功能的，暂缓)
		bodyString.append("{}").append("\r\n");
		byte[] body = bodyString.toString().getBytes();
		System.out.println("body内容组装完成：");
		System.out.println(bodyString.toString());
		// 发送数据 - header段
		byte[] header = new byte[16];
		// 魔数，short类型 (类似 class文件里面的cafebabb) 数据的开头
		byte[] magicArray = ByteUtil.short2bytes((short) 0xdabb);
		System.arraycopy(magicArray, 0, header, 0, 2);
		// 标志：请求/响应， 以及body数据的序列化方式
		header[2] = (byte) 0xC6;
		// 响应状态码
		header[3] = 0x00;
		// messageId(8B)，每次请求的唯一ID
		byte[] messageId = ByteUtil.long2bytes(1);
		System.arraycopy(messageId, 0, header, 4, 8);
		// bodyLength(4B)，后面的内容长度
		byte[] bodyLength = ByteUtil.int2bytes(body.length);
		System.arraycopy(bodyLength, 0, header, 12, 4);
		// 拼装请求报文
		byte[] request = new byte[body.length + header.length];
		System.arraycopy(header, 0, request, 0, header.length);
		System.arraycopy(body, 0, request, 16, body.length);
		dubboClient.write(ByteBuffer.wrap(request));
		ByteBuffer response = ByteBuffer.allocate(1025);
		dubboClient.read(response);
		System.out.println("响应内容：");
		System.out.println(new String(response.array()));
		/**
		 * 结果文件  dubbo-protocol.md
		 * */
	}
}
