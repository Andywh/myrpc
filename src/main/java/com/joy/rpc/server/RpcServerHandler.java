package com.joy.rpc.server;

import com.joy.rpc.common.RpcRequest;
import com.joy.rpc.common.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMember;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by Ai Lun on 2020-08-17.
 */

public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerHandler.class);

    private final Map<String, Object> handlerMap;

    public RpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        // 创建并初始化 RPC 响应对象
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        try {
            Object result = handle(request);
            response.setResult(result);
        } catch (Exception e) {
            LOGGER.error("handler result failure", e);
            response.setException(e);
        }
        // 写入 PRC 响应对象并自动关闭连接
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
    // bean
    // 实例
    // UserService userService = new UserService();
    // com.joy.service.UserService

    private Object handle(RpcRequest request) throws Exception {
        // 获取服务对象
        String serviceName = request.getInterfaceName();
        String serviceVersion = request.getServiceVersion();
        if (StringUtils.isEmpty(serviceName)) {
            serviceName += "-" + serviceVersion;
        }
        Object serviceBean = handlerMap.get(serviceName);
        if (serviceBean == null) {
            throw new RuntimeException(String.format("can not find service bean by key: %s", serviceName));
        }
        // 获取反射调用所需的参数
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        // 使用 CGLib 执行反射调用
        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }

}
