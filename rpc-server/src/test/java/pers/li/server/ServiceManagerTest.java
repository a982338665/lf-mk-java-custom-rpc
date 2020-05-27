package pers.li.server;

import org.junit.Before;
import org.junit.Test;
import pers.li.rpc.Request;
import pers.li.rpc.ServiceDescriptor;
import pers.li.utils.ReflectionUtils;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ServiceManagerTest {

    ServiceManager sm;

    /**
     * 在测试代码之前需要初始化的东西
     */
    @Before
    public void init() {
        sm = new ServiceManager();
        //服务先注册进去
        TestInterface testClass = new TestClass();
        sm.register(TestInterface.class, testClass);
    }

    /**
     * 注册：某类某方法 + 实例
     */
    @Test
    public void register() {
        TestInterface testClass = new TestClass();
        sm.register(TestInterface.class, testClass);
    }

    /**
     * 查找
     */
    @Test
    public void lookUp() {
        //获取方法
        Method method = ReflectionUtils.getPublicMethods(TestInterface.class)[0];
        //获取描述
        ServiceDescriptor from = ServiceDescriptor.from(TestInterface.class, method);
        Request request = new Request();
        request.setServiceDescriptor(from);
        //找服务
        ServiceInstance serviceInstance = sm.lookUp(request);
        System.err.println(serviceInstance.toString());
        assertNotNull(serviceInstance);

    }
}
