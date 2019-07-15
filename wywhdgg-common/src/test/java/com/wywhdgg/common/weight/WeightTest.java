package com.wywhdgg.common.weight;

import com.wywhdgg.common.loadbalance.weight.RandomWeightLoadBalance;
import com.wywhdgg.common.loadbalance.weight.Server;
import java.util.Arrays;

public interface WeightTest {

    public static void main(String[] args) {
        //服务机器
        Server[] servers = {new Server("10.0.0.1", 8081, 20), new Server("10.0.0.2", 8082, 40), new Server("10.0.0.3", 8083, 60)};
        RandomWeightLoadBalance randomWeightLoadBalance = new RandomWeightLoadBalance();
        //选择服务
        Server server = randomWeightLoadBalance.chooseServer(Arrays.asList(servers));
        int threadCount = 100;
        randomWeightLoadBalance.doConcurrently(Arrays.asList(servers), threadCount);
    }
}
