package pers.li.server;

import lombok.extern.slf4j.Slf4j;
import pers.li.rpc.Request;
import pers.li.rpc.ServiceDescriptor;
import pers.li.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理rpc暴露的服务
 */
@Slf4j
public class ServiceManager {

    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }

    /**
     * 服务注册
     * @param interfaceClass
     * @param bean
     * @param <T>
     */
    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (int i = 0; i < publicMethods.length; i++) {
            Method method = publicMethods[i];
            ServiceInstance serviceInstance = new ServiceInstance(bean, method);
            ServiceDescriptor from = ServiceDescriptor.from(interfaceClass, method);
            services.put(from, serviceInstance);
            log.info("register service: {} {}", from.getClazz(), from.getMethod());
        }

    }

    /**
     * 服务查找
     * @param request
     * @return
     */
    public ServiceInstance lookUp(Request request) {
        ServiceInstance serviceInstance = services.get(request.getServiceDescriptor());
        return serviceInstance;
    }
}
