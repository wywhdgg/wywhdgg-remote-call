package com.wywhdgg.common.loadbalance.weight;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 需求：后端有三台机器，信息分别为，

 S1<ip:"10.0.0.1",port:8081,weight:20>,

 S2<ip:"10.0.0.2",port:8082,weight:40>, 

 S3<ip:"10.0.0.3",port:8083,weight:60>，

 根据weight按照比例返回响应的机器信息。
    算法思路：将三个权重映射到一个一维空间中，
 那么S1对应区间[0, 20),
 S2对应区间[20, 60), [60, 120]，
 然后在[0,120]之间生成随机数，看此数落在哪个区间，那么就返回对应机器的信息。
 https://blog.csdn.net/lavorange/article/details/78320349
* */
public class WeightAllocationAlg {
    private AtomicInteger[] completedCount = new AtomicInteger[3];

    public WeightAllocationAlg() {
        completedCount[0] = new AtomicInteger(0);
        completedCount[1] = new AtomicInteger(0);
        completedCount[2] = new AtomicInteger(0);
    }

    private int getServerByWeight(int[] weightArr) {
        int[][] randArr = new int[weightArr.length][2];
        int totalRank = 0;
        int index = 0;
        for (int i = 0; i < weightArr.length; i++) {
            if (weightArr[i] <= 0) {
                continue;
            }

            totalRank += weightArr[i];
            randArr[i][0] = i;
            randArr[i][1] = totalRank;
        }
        System.out.println("randArr="+JSON.toJSONString(randArr));
        int hitRank = new Random().nextInt(totalRank) + 1;//[1, totalRand]
        for (int i = 0; i < randArr.length; i++) {
            if (hitRank <= randArr[i][1]) {
                return randArr[i][0];
            }
        }

        return randArr[0][0];
    }

    public Server choose(List<Server> serverList) {
        System.out.println("serverList size ="+serverList.size());
        if (null == serverList) {
            return null;
        }

        int[] weightArr = new int[serverList.size()];

        for (int i = 0; i < serverList.size(); i++) {
            if (serverList.get(i).getWeight() > 0) {
                weightArr[i] = serverList.get(i).getWeight();
            }
        }
        System.out.println("weightArr="+ JSON.toJSONString(weightArr));
        if (weightArr.length == 0) {
            return null;
        }

        int chosenIndex = getServerByWeight(weightArr);
        System.out.println("chosenIndex:"+chosenIndex);
        return serverList.get(chosenIndex);
    }

    public void doTestConcurrently(final List<Server> servers, int threadCount) {
        class MyRunnable implements Runnable {
            @Override
            public void run() {
                Server svr = choose(servers);
                if (svr.getIp().equals("10.0.0.1")) {
                    completedCount[0].incrementAndGet();
                } else if (svr.getIp().equals("10.0.0.2")) {
                    completedCount[1].incrementAndGet();
                } else if (svr.getIp().equals("10.0.0.3")) {
                    completedCount[2].incrementAndGet();
                }
            }
        }

        try {
            Thread[] ts = new Thread[threadCount];
            for (int i = 0; i < threadCount; i++) {
                ts[i] = new Thread(new MyRunnable());
            }

            for (int i = 0; i < threadCount; i++) {
                ts[i].start();
            }

            for (int i = 0; i < threadCount; i++) {
                ts[i].join();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            int totalCompleted = completedCount[0].get() + completedCount[1].get() + completedCount[2].get();
            if (totalCompleted == threadCount) {
                System.out.println((double) completedCount[0].get() / totalCompleted);
                System.out.println((double) completedCount[1].get() / totalCompleted);
                System.out.println((double) completedCount[2].get() / totalCompleted);
            }
        }
    }

    public static void main(String[] args) {
        Server[] servers = {new Server("10.0.0.1", 8081, 20), new Server("10.0.0.2", 8082, 40), new Server("10.0.0.3", 8083, 60)};
        WeightAllocationAlg weightAllocationAlg = new WeightAllocationAlg();
        Server server = weightAllocationAlg.choose(Arrays.asList(servers));
        System.out.println(server.toString());
        int threadCount = 100;
        weightAllocationAlg.doTestConcurrently(Arrays.asList(servers), threadCount);
    }
}
