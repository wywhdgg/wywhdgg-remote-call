package com.wywhdgg.common.queue;

import com.wywhdgg.common.util.SleepUtil;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Cook extends Thread {

    private Queue<Food> queue;

    public Cook(Queue<Food> git , String name) {
        super(name);
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            SleepUtil.randomSleep();    //模拟厨师炒菜时间
            Food food = new Food();
           log.info(getName() + " 生产了{}" ,food);
            synchronized (queue) {
                while (queue.size() > 4) {
                    try {
                        log.info("队列元素超过5个，为：" + queue.size() + " " + getName() + "抽根烟等待中");
                        queue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                //饭菜已经做好，添加队列中
                queue.add(food);
                //唤醒其它正在等待的队列
                queue.notifyAll();
            }
        }
    }
}