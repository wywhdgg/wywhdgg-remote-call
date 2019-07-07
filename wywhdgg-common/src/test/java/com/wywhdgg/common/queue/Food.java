package com.wywhdgg.common.queue;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Food {
    private static int counter = 0;
    private int i;  //代表生产的第几个菜

    public Food() {
        i = ++counter;
    }

    @Override
    public String toString() {
        return "第" + i + "个菜";
    }
}