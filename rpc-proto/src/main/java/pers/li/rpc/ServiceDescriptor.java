package pers.li.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示服务
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {

    private String calzz;
    private String method;
    private String returnType;
    private String[] parameterTypes;

}
