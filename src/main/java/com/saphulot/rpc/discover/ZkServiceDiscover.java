package com.saphulot.rpc.discover;

import com.saphulot.rpc.discover.loadbalance.RandomLoadBalance;
import com.saphulot.rpc.register.ZkRegisterCenter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
public class ZkServiceDiscover implements IServiceDiscover {
    Map<String, List<String>> serviceAddressMap = new ConcurrentHashMap<>();
    private CuratorFramework curatorFramework;
    private String connectionAddress;

    private RandomLoadBalance iLoadBalance;


    public ZkServiceDiscover(String connectionAddress) {
        this.connectionAddress = connectionAddress;

        iLoadBalance = new RandomLoadBalance();
        //初始化zk连接
        curatorFramework = CuratorFrameworkFactory.builder().connectString(connectionAddress).sessionTimeoutMs(15000)
                .retryPolicy(new ExponentialBackoffRetry(1000,10)).build();
        curatorFramework.start();
    }



    @Override
    public String discover(String serviceName) {
        List<String> serviceAddr;

        if(!serviceAddressMap.containsKey(serviceName)){
            String path = ZkRegisterCenter.ZK_REGISTER_PATH + "/" + serviceName;
            System.out.println(path);
            try {
                serviceAddr = curatorFramework.getChildren().forPath(path);
                serviceAddressMap.put(serviceName, serviceAddr);
                registerWatcher(serviceName);
            } catch (Exception e) {
                if (e instanceof KeeperException.NoNodeException) {
                    log.error("未获得该节点,serviceName:{}", serviceName);
                    serviceAddr = null;
                } else {
                    throw new RuntimeException("获取子节点异常：" + e);
                }
            }

        }else {
            serviceAddr = serviceAddressMap.get(serviceName);
        }

        return iLoadBalance.selectServiceAddress(serviceAddr);
    }

    /**
     * 注册监听
     * @param serviceName 服务名称
     */
    private void registerWatcher(String serviceName) {
        String path = ZkRegisterCenter.ZK_REGISTER_PATH + "/" + serviceName;

        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, path, true);

        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework, pathChildrenCacheEvent) -> {
            List<String> serviceAddresses = curatorFramework.getChildren().forPath(path);
            serviceAddressMap.put(serviceName, serviceAddresses);
        };

        childrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            childrenCache.start();
        } catch (Exception e) {
            throw new RuntimeException("注册PatchChild Watcher 异常" + e);
        }

    }

}
