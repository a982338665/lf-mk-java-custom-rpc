package pers.li.client;

import pers.li.codec.Decoder;
import pers.li.codec.Encoder;
import pers.li.utils.ReflectionUtils;

import java.lang.reflect.Proxy;

public class RpcClient {

    private RpcClientConfig config;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RpcClient() {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig config) {
        this.config = config;
        //codec
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        this.selector = ReflectionUtils.newInstance(config.getSelectorClass());
        this.selector.init(this.config.getServers(), config.getConnectCount(), config.getTransportClass());
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(),new Class[]{clazz},new RemoteHandler(clazz,encoder,decoder,selector));
    }
}
