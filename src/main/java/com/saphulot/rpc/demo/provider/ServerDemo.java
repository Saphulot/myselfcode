package com.saphulot.rpc.demo.provider;

import com.saphulot.rpc.demo.api.HelloWorld;
import com.saphulot.rpc.register.ZkRegisterCenter;
import com.saphulot.rpc.server.RpcServer;

import java.util.Arrays;

public class ServerDemo {

    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorldImpl();
        HelloWorld helloWorld1 = new HelloWorldVersion();
        ZkRegisterCenter zkRegisterCenter = new ZkRegisterCenter("127.0.0.1:2181");
        RpcServer rpcServer = new RpcServer("127.0.0.1", 8088, zkRegisterCenter);
        rpcServer.bindService(Arrays.asList(helloWorld,helloWorld1));
        rpcServer.publish();
    }
}
