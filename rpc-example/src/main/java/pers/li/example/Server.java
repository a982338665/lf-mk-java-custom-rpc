package pers.li.example;

import pers.li.server.RPCServer;

public class Server {
    public static void main(String[] args) {
        RPCServer server = new RPCServer();
        server.register(CalcService.class, new CalcServiceImpl());
        server.start();
    }
}
