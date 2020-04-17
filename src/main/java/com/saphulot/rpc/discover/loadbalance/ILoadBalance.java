package com.saphulot.rpc.discover.loadbalance;

import java.util.List;

public interface ILoadBalance {
    /**
     * 在已有的服务列表中选一个路径
     * @param serviceAddr 服务地址列表
     * @return 服务地址
     */
    String selectServiceAddress(List<String> serviceAddr);
}
