package com.saphulot.rpc.demo.provider;

import com.saphulot.rpc.annotation.RpcService;
import com.saphulot.rpc.demo.api.HelloWorld;
@RpcService(HelloWorld.class)
public class HelloWorldVersion implements HelloWorld {
    @Override
    public String sayHelloWorld(String str) {
        return "HelloWorldVersion 1.1.sayHelloWorld" + str;
    }
}
