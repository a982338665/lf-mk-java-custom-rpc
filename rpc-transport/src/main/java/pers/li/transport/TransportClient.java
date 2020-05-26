package pers.li.transport;

import pers.li.rpc.Peer;

import java.io.InputStream;

/**
 * 1.创建连接
 * 2.发送数据等待响应
 * 3.关闭连接
 */
public interface TransportClient {

    void connect(Peer peer);

    InputStream write(InputStream inputStream);

    void close();
}
