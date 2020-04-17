package com.saphulot.rpc.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.saphulot.rpc.common.RpcRequest;
import com.saphulot.rpc.common.RpcResponse;
import com.saphulot.rpc.common.ResponseCode;
import com.saphulot.rpc.discover.IServiceDiscover;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class RpcInvicationHandler implements InvocationHandler {

    private String version;


    private IServiceDiscover serverDiscover;

    public RpcInvicationHandler(String version, IServiceDiscover serverDiscover) {
        this.version = version;
        this.serverDiscover = serverDiscover;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();

        String serviceName = method.getDeclaringClass().getName();
        request.setClassName(serviceName);
        request.setMethodName(method.getName());
        request.setParams(args);
        request.setVersion(version);

        if (null != version && !"".equals(version)) {
            serviceName += "-" + version;
        }

        String servicePath = serverDiscover.discover(serviceName);

        if (null == servicePath) {
            log.error("并未找到服务地址,className:{}", serviceName);
            throw new RuntimeException("未找到服务地址");
        }

        String host = servicePath.split(":")[0];
        int port = Integer.parseInt(servicePath.split(":")[1]);

        RpcResponse response = new NettyTransport(host, port).send(request);

        if (response == null) {
            throw new RuntimeException("调用服务失败,servicePath:" + servicePath);
        }
        if (response.getCode() == null || !response.getCode().equals(ResponseCode.SUCCESS.getValue())) {
            log.error("调用服务失败,servicePath:{},RpcResponse:{}", servicePath,
                    JSONObject.toJSONString(JSON.toJSONString(response)));
            throw new RuntimeException(response.getMessage());
        } else {
            return response.getData();
        }
    }
}
