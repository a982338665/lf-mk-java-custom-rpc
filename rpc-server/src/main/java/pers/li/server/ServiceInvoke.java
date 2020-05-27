package pers.li.server;

import pers.li.rpc.Request;
import pers.li.utils.ReflectionUtils;

/**
 * 调用具体服务
 * 调用service实例的辅助类
 */
public class ServiceInvoke {

    public Object invoke(ServiceInstance serviceInstance, Request request) {
        return ReflectionUtils.invoke(serviceInstance.getTarget(), serviceInstance.getMethod(),request.getParameters());
    }


}
