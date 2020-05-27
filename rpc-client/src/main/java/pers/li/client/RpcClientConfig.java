package pers.li.client;

import lombok.Data;
import pers.li.codec.Decoder;
import pers.li.codec.Encoder;
import pers.li.codec.JSONDecoder;
import pers.li.codec.JSONEncoder;
import pers.li.rpc.Peer;
import pers.li.transport.HTTPTransportClient;
import pers.li.transport.TransportClient;

import java.util.Arrays;
import java.util.List;

/**
 * client 配置
 */
@Data
public class RpcClientConfig {

    private Class<? extends TransportClient> transportClass = HTTPTransportClient.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;
    private int connectCount = 1;
    private List<Peer> servers = Arrays.asList(
            new Peer("127.0.0.1", 3000)
    );

}
