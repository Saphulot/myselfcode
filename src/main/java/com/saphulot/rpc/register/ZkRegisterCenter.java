package com.saphulot.rpc.register;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
@Slf4j
public class ZkRegisterCenter implements IRegisterCenter {
    //zk注册服务根节点
    public static final String ZK_REGISTER_PATH = "/rpc";

    private String connectionAddress;

    private CuratorFramework curatorFramework;

    public ZkRegisterCenter(String connectionAddress) {
        this.connectionAddress = connectionAddress;
        //初始化zk连接
        curatorFramework = CuratorFrameworkFactory.builder().connectString(connectionAddress).sessionTimeoutMs(15000)
                .retryPolicy(new ExponentialBackoffRetry(1000,10)).build();
        curatorFramework.start();
    }

    @Override
    public void register(String serviceName, String address) throws Exception {
        String servicePath = ZK_REGISTER_PATH + "/" + serviceName;

        String addr = servicePath + "/" + address;
        //创建临时节点
        String nodePath = curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .forPath(addr, "".getBytes());
        log.info("创建节点成功，节点为{}",nodePath);
    }
}
