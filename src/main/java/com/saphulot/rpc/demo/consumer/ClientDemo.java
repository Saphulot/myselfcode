package com.saphulot.rpc.demo.consumer;

import com.saphulot.rpc.client.RpcClientProxy;
import com.saphulot.rpc.demo.api.HelloWorld;
import com.saphulot.rpc.discover.ZkServiceDiscover;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientDemo {
    public static void main(String[] args) throws InterruptedException {
        ZkServiceDiscover zkServiceDiscover = new ZkServiceDiscover("127.0.0.1:2181");
        RpcClientProxy rpcClientProxy = new RpcClientProxy(zkServiceDiscover);

        try {
            HelloWorld helloWorld = rpcClientProxy.clientProxy(HelloWorld.class,"1.1");
            System.out.println(helloWorld.sayHelloWorld("saphulot"));
        }catch (Exception e){
            e.printStackTrace();
        }
        //测试负载均衡
        for (int i = 0; i < 10; i++) {
            HelloWorld helloService = rpcClientProxy.clientProxy(HelloWorld.class,"");
            try {
                String result=helloService.sayHelloWorld("xxx");
                System.out.println(result);
            } catch (Exception e) {
                log.error("调用失败：e:{}",e.toString());
            }

            Thread.sleep(2000);
        }
    }
}
