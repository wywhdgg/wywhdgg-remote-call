package com.wywhdgg.common.loadbalance.weight;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: dongzhb
 * @date: 2019/7/15
 * @Description:
 */
public class RandomWeightLoadBalance {
    /**
     * S1<ip:"10.0.0.1",port:8081,weight:20>, S2<ip:"10.0.0.2",port:8082,weight:40>,  S3<ip:"10.0.0.3",port:8083,weight:60>， /*
     *
     * /**调用次数
     */
    private AtomicInteger[] completedCount = new AtomicInteger[3];

    public RandomWeightLoadBalance() {
        completedCount[0] = new AtomicInteger(0);
        completedCount[1] = new AtomicInteger(0);
        completedCount[2] = new AtomicInteger(0);
    }

    /**
     * 获取服务机器权重值
     */
    public Server getWeightValue(List<Server> serverList) {
        if (null == serverList || serverList.size() == 0) {
            return null;
        }
        int[] weightArray = new int[serverList.size()];
        for (int i = 0; i < serverList.size(); i++) {
            weightArray[i] = serverList.get(i).getWeight();
        }

        if (weightArray.length == 0) {
            return null;
        }
//        System.out.println("server weight value list = " + JSON.toJSONString(weightArray));
        int index = getServerByWeight(weightArray);
        return serverList.get(index);
    }

    private int getServerByWeight(int[] weightArray) {
        int[][] randArr = new int[weightArray.length][2];
        int totalRank = 0;
        for (int i = 0; i < weightArray.length; i++) {
            if (weightArray[i] <= 0) {
                continue;
            }
            totalRank += weightArray[i];
            randArr[i][0] = i;
            randArr[i][1] = totalRank;
        }
//        System.out.println("randArr=" + JSON.toJSONString(randArr));
        int hitRank = new Random().nextInt(totalRank) + 1;
//        System.out.println("hitRank=" + hitRank);
        for (int i = 0; i < randArr.length; i++) {
            if (hitRank <= randArr[i][1]) {
                return randArr[i][0];
            }
        }
        return randArr[0][0];
    }

    public static void main(String[] args) {
        //服务机器
        Server[] servers = {new Server("10.0.0.1", 8081, 20), new Server("10.0.0.2", 8082, 40), new Server("10.0.0.3", 8083, 60)};
        RandomWeightLoadBalance randomWeightLoadBalance = new RandomWeightLoadBalance();
        Server server = randomWeightLoadBalance.getWeightValue(Arrays.asList(servers));
        System.out.println(server);
    }
}
