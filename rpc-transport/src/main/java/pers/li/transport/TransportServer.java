package pers.li.transport;

/**
 * 1.启动，监听端口
 * 2.接收请求
 * 3.关闭监听
 */
public interface TransportServer {

    void init(int port,RequestHandler requestHandler);

    void start();

    void stop();

}
