package pers.li.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * RPC请求
 */
@Data
public class Request {

    private ServiceDescriptor serviceDescriptor;
    private Object[] parameters;
}
