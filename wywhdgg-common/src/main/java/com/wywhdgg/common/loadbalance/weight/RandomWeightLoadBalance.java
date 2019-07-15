package com.wywhdgg.common.loadbalance.weight;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: dongzhb
 * @date: 2019/7/15
 * @Description:
 * 算法思路：将三个权重映射到一个一维空间中，那么
 * S1对应区间[0, 20),
 * S2对应区间[20, 60),
 * [60, 120]，
 * 然后在[0,120]之间生成随机数，看此数落在哪个区间，那么就返回对应机器的信息。
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
    public Server chooseServer(List<Server> serverList) {
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
            /**
             * [[0,20][1,60],[2,120]]
             * */
            totalRank += weightArray[i];
            randArr[i][0] = i;
            randArr[i][1] = totalRank;
        }
        int hitRank = new Random().nextInt(totalRank) + 1;
        for (int i = 0; i < randArr.length; i++) {
            if (hitRank <= randArr[i][1]) {
                return randArr[i][0];
            }
        }
        return randArr[0][0];
    }


    public void doConcurrently(final List<Server> servers, int threadCount) {
        class MyRunnable implements Runnable {
            @Override
            public void run() {
                Server svr = chooseServer(servers);
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
            /**创建线程*/
            Thread[] ts = new Thread[threadCount];
            for (int i = 0; i < threadCount; i++) {
                ts[i] = new Thread(new MyRunnable());
            }
            /**执行线程*/
            for (int i = 0; i < threadCount; i++) {
                ts[i].start();
            }
            /**清除线程*/
            for (int i = 0; i < threadCount; i++) {
                ts[i].join();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            /**计算权重*/
            int totalCompleted = completedCount[0].get() + completedCount[1].get() + completedCount[2].get();
            System.out.println("totalCompleted="+totalCompleted);
            System.out.println("threadCount="+threadCount);
            /**当前总次数==线程数*/
            if (totalCompleted == threadCount) {
                System.out.println((double) completedCount[0].get() / totalCompleted);
                System.out.println((double) completedCount[1].get() / totalCompleted);
                System.out.println((double) completedCount[2].get() / totalCompleted);
            }
        }
    }


    public static void main(String[] args) {
        //服务机器
        Server[] servers = {new Server("10.0.0.1", 8081, 20), new Server("10.0.0.2", 8082, 40), new Server("10.0.0.3", 8083, 60)};
        RandomWeightLoadBalance randomWeightLoadBalance = new RandomWeightLoadBalance();
        Server server = randomWeightLoadBalance.chooseServer(Arrays.asList(servers));
        System.out.println(server);
    }
}
