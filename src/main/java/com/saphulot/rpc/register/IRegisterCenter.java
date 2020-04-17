package com.saphulot.rpc.register;


public interface IRegisterCenter {
    /**
     * 注册服务
     * @param serviceName 服务名称
     * @param address 服务地址
     * @throws Exception 注册失败
     */
    void register(String serviceName, String address) throws Exception;
}
