package com.saphulot.rpc.demo.provider;

import com.saphulot.rpc.annotation.RpcService;
import com.saphulot.rpc.demo.api.HelloWorld;
@RpcService(HelloWorld.class)
public class HelloWorldImpl2 implements HelloWorld {
    @Override
    public String sayHelloWorld(String str) {
        return "HelloWorldImpl2 say " + str;
    }
}
