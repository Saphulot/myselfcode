package com.saphulot.rpc.server;

import com.saphulot.rpc.annotation.RpcService;
import com.saphulot.rpc.register.IRegisterCenter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RpcServer {
    /**
     * 服务发布地址ip
     */
    private String serviceIp;

    private int servicePort;

    /**
     * 注册中心
     */
    private IRegisterCenter registerCenter;

    public RpcServer(String serviceIp, int servicePort, IRegisterCenter registerCenter) {
        this.serviceIp = serviceIp;
        this.servicePort = servicePort;
        this.registerCenter = registerCenter;
    }

    /**
     * 服务名称和服务对象关系
     */
    private Map<String, Object> serviceMap = new HashMap<>();

    /**
     * 绑定服务名称和对象
     */
    public void bindService(List<Object> services) {
        for (Object service : services) {
            RpcService annotation = service.getClass().getAnnotation(RpcService.class);
            if (annotation == null) {
                throw new RuntimeException("服务没有rpc注解，请检查 " + service.getClass().getName());
            }

            String serviceName = annotation.value().getName();
            String version = annotation.version();
            if (!"".equals(version)) {
                serviceName += "-" + version;
            }

            serviceMap.put(serviceName, service);
        }
    }

    public void publish() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();

        bootstrap.group(loopGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast(new LengthFieldPrepender(4));
                        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new ProcessRequestHandler(serviceMap));
                    }
                });
        bootstrap.bind(serviceIp, servicePort);
        log.info("成功启动服务，host:{},port:{}", serviceIp, servicePort);

        //将所有的服务名称注册进注册中心
        serviceMap.forEach((serviceName, object) -> {
            try {
                registerCenter.register(serviceName, serviceIp + ":" + servicePort);
            } catch (Exception e) {
                log.error("服务注册失败,e:{}", e.getMessage());
                throw new RuntimeException("服务注册失败");
            }
            log.info("成功注册服务，服务名称：{},服务地址：{}", serviceName, serviceIp + ":" + servicePort);
        });
    }


}
