package pers.li.example;

import pers.li.client.RpcClient;

public class Client {

    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        CalcService proxy = client.getProxy(CalcService.class);
        int add = proxy.add(1, 2);
        int minus = proxy.minus(1, 2);
        System.err.println(add + "||" + minus);
    }
}
