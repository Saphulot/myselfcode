package com.saphulot.rpc.discover;

public interface IServiceDiscover {
    /**
     * 基于服务名称发现服务地址
     * @return 远程地址
     */
    String discover(String serviceName);
}
