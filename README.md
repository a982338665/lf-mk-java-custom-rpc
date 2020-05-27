# customRPC
自己动手实现rpc框架：https://www.imooc.com/learn/1158

**1.大纲：**
    
    ·理论：rpc核心原理，技术介绍
    ·实战：gk-rpc代码实现，案例
    ·总结：
    
**2.理论：**
    
    1.概念：
        RPC：Remote Procedure Call，即远程过程调用
            分布式常见的通讯方式，从跨进程到跨物理机已有几十年历史
        跨进程交互形式：
            RESTFUL
            WEBSERVICE
            HTTP
            基于DB做数据交换
            基于MQ做数据交换
    2.交互形式对比：
        1.依赖中间件做数据交互：异步
            系统A -> 数据存储（DB，MQ,redis） -> 系统B
            常用：mysql，rabbitmq，kafka，redis，可用性要求较高的时候，常用mysql，rabbitmq做存储中间件，任务存储没任务分发
        2.直接交互：同步
            客户端 -> http,rpc,webservice -> 服务端
            常用：restful，webservice，http，rpc
        3.rpc中：
            server：Provider 服务提供者
            client：Consumer 服务消费者
            Stub 存根，服务描述
            
**3.现有框架对比：**

 |xx      |grpc     |thrift     |RMI        |dubbo      |hadoopRPC|
 |----    |----     |----       |----       |----       |----|
 |开发语言|多语言   |多语言     |java       |java       |java|
 |序列化  |protobuf |thrift格式 |Java序列化 |hession2   |R/Writable|
 |注册中心|false    |fasle      |jdk自带    |zk等       |false|
 |跨语言  |true     |true       |false      |false      |false|
 |服务定义|protobuf |thrift文件 |Java接口   |Java接口   |java接口|
 |服务治理|false    |false      |false      |true       |false|
        
**4.核心原理：三部分组成**
    
    server      服务提供
    client      服务消费
    registry    服务注册与发现
    
**5.技术栈：**
    
    1.基础：javacore ，maven ，反射
    2.动态代理：生成client存根实际调用对象 java动态代理
    3.序列化：java对象与二进制数据互转 fastjson
    4.网络通信：用来传输序列化后的数据 jetty，URLConnection

**6.代码实现：**
    
    1.建工程
    2.实现序列化模块
    3.实现网络模块
    4.实现server
    5.实现client
    6.gk-rpc使用案例

**7.创建协议：**
    
    D:\git-20191029\customRPC\rpc-proto\src\main\java\pers\li\rpc
    
**8.总结：**
    
    问题：对网络协议理解较浅
    不足：
        安全性
        服务端处理能力 
        注册中心
        集成能力                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
    
**9.理解：**
    
    rpc-proto ： 基础协议封装
        Peer：主机+端口
        ServiceDescriptor：服务描述，将会是注册中心中对应的【服务的key值】包含有【类，方法，返回值类型，参数类型数组】，唯一确定一个方法
        Request：请求体,持有【服务描述+请求参数数组】
        Response：默认的响应体封装
    rpc_transport: 网络服务封装
        TransportClient：客户端封装【接口+实现】
            1.创建连接
            2.发送数据等待响应：发送inputstream，等待outputstream
            3.关闭连接
        TransportServer:服务端封装【接口+实现】
            1.启动监听: servlet管理
            2.接收请求：接收请求，反序列化获取对象，处理调用，返回数据
            3.关闭监听
    rpc-common: 工具类封装
        ReflectionUtils：
            根据class创建对象
            根据class获取该类所有公共方法
            invoke方法调用 ： public static Object invoke(Object object, Method method, Object... args)
    rpc-codec: 序列化封装【接口+实现】
        Encoder 转二进制
        Decoder 转对象
    rpc-server: 服务端封装
        RpcServerConfig：服务配置类
            HTTPTransportServer：默认服务实例类
            JSONEncoder：序列化实例类
            JSONDecoder：反序列化实例类
            port：监听端口
        ServiceInstance：服务的实例 --> 哪个对象暴露出哪个方法
            target：对象
            method：方法
        ServiceManager：管理rpc的所有服务
            Map<ServiceDescriptor, ServiceInstance> services：要将服务描述，服务实例作为key-value存储，便于客户端传来时，能够找到准确地实例，调用正确的方法
            register：服务注册【register(Class<T> interfaceClass, T bean) 】
                接口类 + 对象bean：将对象中的每一个方法都当做一个ServiceInstance注册进map中
            lookup：服务查找【lookUp(Request request)】
                获取请求中的ServiceDescriptor，去map中取出
        ServiceInvoke：【服务的调用】
            invoke(ServiceInstance serviceInstance, Request request):
                通过request的ServiceDescriptor找到服务的实例
                通过反射调用方法，传入参数
                ReflectionUtils.invoke(serviceInstance.getTarget(), serviceInstance.getMethod(),request.getParameters())
        RPCServer：【服务的封装】
            1.设置RpcServerConfig config
            2.反射获取网络实例     ReflectionUtils.newInstance(config.getTransportClass());
            3.反射获取序列化实例   ReflectionUtils.newInstance(config.getEncoderClass());
            4.反射获取反序列化实例 ReflectionUtils.newInstance(config.getDecoderClass());
            5.创建服务调用对象： this.serviceInvoke = new ServiceInvoke();
            6.创建服务管理对象：this.serviceManager = new ServiceManager();
            7.初始化网络实例：this.net.init(config.getPort(), this.handler); 此时只是准备好服务信息，并未开启监听
            
            8.register：服务注册 register(Class<T> interfaceClass, T bean) {serviceManager.register(interfaceClass, bean); }
            9.start：this.net.start 开启
            10.stop：this.net.stop  关闭
            11.handler请求处理：
                1.接收inputStream
                2.反序列化获得Request
                3.根据ServiceManager.lookup(request)找到实例ServiceInstance
                4.通过Object invoke = serviceInvoke.invoke(sis, request);得到响应结果并封装到 response.setData(invoke);
                5.序列化Response
                6.write
    rpc-client: 客户端封装
         
        
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
