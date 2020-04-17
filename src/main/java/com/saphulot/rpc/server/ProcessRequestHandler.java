package com.saphulot.rpc.server;

import com.alibaba.fastjson.JSON;
import com.saphulot.rpc.common.ResponseCode;
import com.saphulot.rpc.common.RpcRequest;
import com.saphulot.rpc.common.RpcResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
@Slf4j
public class ProcessRequestHandler extends SimpleChannelInboundHandler<String> {
    /**
     * 请求的服务映射
     */
    private Map<String,Object> serviceMap;

    public ProcessRequestHandler(Map<String, Object> serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        log.info("收到服务请求："+s);
        System.out.println(s);
        Object result = this.invoke(JSON.parseObject(s, RpcRequest.class));

        ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(JSON.toJSONString(result));

        channelFuture.addListener(ChannelFutureListener.CLOSE);

    }


    /**
     * 服务调用返回处理的结果
     */
    private Object invoke(RpcRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        /**
         * 获取服务接口
         */
        String className = request.getClassName();
        /**
         * 获取服务方法名
         */
        String methodName = request.getMethodName();

        /**
         * 获取版本号
         */
        String version = request.getVersion();

        /**
         * 获取参数列表
         */
        Object[] params = request.getParams();

        /**
         * 参数类型数据
         */
        Class<?>[] args = Arrays.stream(params).map(Object::getClass).toArray(Class<?>[]::new);

        if (version != null && !"".equals(version)){
            className = className + "-" + version;
        }
        /**
         * 获取对应的服务对象
         */
        Object service = serviceMap.get(className);

        if (null == service) {
            return RpcResponse.fail(ResponseCode.ERROR404, "未找到服务");
        }

        Method method = service.getClass().getMethod(methodName,args);

        if (null == method) {
            return RpcResponse.fail(ResponseCode.ERROR404, "未找到服务方法");
        }


        return RpcResponse.success(method.invoke(service,params));
    }
}
