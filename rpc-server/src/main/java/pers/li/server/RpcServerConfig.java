package pers.li.server;

import lombok.Data;
import pers.li.codec.Decoder;
import pers.li.codec.Encoder;
import pers.li.codec.JSONDecoder;
import pers.li.codec.JSONEncoder;
import pers.li.transport.HTTPTransportServer;
import pers.li.transport.TransportServer;

/**
 * server配置
 */
@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    private int port = 3000;

}
