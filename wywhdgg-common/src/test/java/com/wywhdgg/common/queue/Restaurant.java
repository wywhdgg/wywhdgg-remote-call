package com.wywhdgg.common.queue;

import java.util.LinkedList;
import java.util.Queue;

public class Restaurant {

    public static void main(String[] args) {
        Queue<Food> queue = new LinkedList<>();
        new Cook(queue, "1号厨师").start();
        new Cook(queue, "2号厨师").start();
        new Cook(queue, "3号厨师").start();
        new Waiter(queue, "1号服务员").start();
        new Waiter(queue, "2号服务员").start();
        new Waiter(queue, "3号服务员").start();
    }
}