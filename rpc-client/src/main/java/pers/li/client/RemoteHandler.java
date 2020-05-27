package pers.li.client;

import lombok.extern.slf4j.Slf4j;
import pers.li.codec.Decoder;
import pers.li.codec.Encoder;
import pers.li.rpc.Request;
import pers.li.rpc.Response;
import pers.li.rpc.ServiceDescriptor;
import pers.li.transport.TransportClient;
import sun.misc.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 调用远程服务的代理类
 */
@Slf4j
public class RemoteHandler implements InvocationHandler {

    private Class clazz;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RemoteHandler(Class clazz, Encoder encoder, Decoder decoder, TransportSelector transportSelector) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = transportSelector;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setServiceDescriptor(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);

        Response response = remoteInvoke(request);
        if (response == null || response.getCode() != 0) {
            throw new IllegalStateException("fail to invoke remote: " + response);
        }
        return response.getData();
    }

    private Response remoteInvoke(Request request) {
        TransportClient client = null;
        Response decode = null;
        try {
            client = selector.select();
            byte[] encode = encoder.encode(request);
            InputStream receive = client.write(new ByteArrayInputStream(encode));
            byte[] bytes = IOUtils.readFully(receive, receive.available(), true);
            decode = decoder.decode(bytes, Response.class);

        } catch (IOException e) {
            decode = new Response();
            decode.setCode(1);
            decode.setMessage("RpcClient get Error:" + e.getClass() + "\n:" + e.getMessage());
            log.warn(e.getMessage(), e);
        } finally {
            if (client != null) {
                selector.release(client);
            }
        }
        return decode;
    }
}
