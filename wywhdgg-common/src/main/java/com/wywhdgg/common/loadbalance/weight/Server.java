package com.wywhdgg.common.loadbalance.weight;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Server {
    private String ip;
    private int port;
    private int weight;

    public Server(String ip, int port, int weight) {
        this.ip = ip;
        this.port = port;
        this.weight = weight;
    }
}
