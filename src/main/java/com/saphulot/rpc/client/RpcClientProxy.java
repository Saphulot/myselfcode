package com.saphulot.rpc.client;

import com.saphulot.rpc.discover.IServiceDiscover;

import java.lang.reflect.Proxy;

public class RpcClientProxy {
    private IServiceDiscover serviceDiscover;

    public RpcClientProxy(IServiceDiscover serviceDiscover) {
        this.serviceDiscover = serviceDiscover;
    }

    public <T> T clientProxy(Class<T> interfaceCls, String version){
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),new Class[]{interfaceCls},
                new RpcInvicationHandler(version,serviceDiscover));
    }
}
