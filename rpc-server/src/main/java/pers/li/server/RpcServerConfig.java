package pers.li.server;

import lombok.Data;
import pers.li.codec.Decoder;
import pers.li.codec.Encoder;
import pers.li.transport.HTTPTransportServer;
import pers.li.transport.TransportServer;

/**
 * server配置
 */
@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;
    private Class<? extends Encoder> encoderClass = Encoder.class;
    private Class<? extends Decoder> decoderClass = Decoder.class;

    private int port = 3000;

}
