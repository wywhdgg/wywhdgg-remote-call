package com.wywhdgg.common.queue;

import com.wywhdgg.common.util.SleepUtil;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class Waiter extends Thread {

    private Queue<Food> queue;

    public Waiter(Queue<Food> queue, String name) {
        super(name);
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            Food food;
            synchronized (queue) {
                while (queue.size() < 1) {
                    try {
                        log.info("队列元素个数为：" + queue.size() + "，" + getName() + "抽根烟等待中");
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                food = queue.remove();
                log.info(getName() + " 获取到：" + food);
                queue.notifyAll();
            }
            SleepUtil.randomSleep();    //模拟服务员端菜时间
        }
    }
}