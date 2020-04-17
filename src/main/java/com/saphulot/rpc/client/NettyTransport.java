package com.saphulot.rpc.client;

import com.alibaba.fastjson.JSON;
import com.saphulot.rpc.common.RpcRequest;
import com.saphulot.rpc.common.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyTransport {

    private static Bootstrap bootstrap;

    private String host;

    private int port;

    public NettyTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group).channel(NioSocketChannel.class);

        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                //处理数据的粘包、拆包问题
                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast(new LengthFieldPrepender(4));
                pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast(new ClientHandler());
            }
        });
    }

    public RpcResponse send(RpcRequest request) throws InterruptedException {
        ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

        Channel channel = channelFuture.channel();

        channel.writeAndFlush(JSON.toJSONString(request));

        //当通道关闭了，就继续往下走
        //实现同步调用
        channel.closeFuture().sync();
        AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
        return channel.attr(key).get();
    }


    private static class ClientHandler extends SimpleChannelInboundHandler<String> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
            log.info("收到response："+s);
            RpcResponse rpcResponse = JSON.parseObject(s, RpcResponse.class);

            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");

            channelHandlerContext.channel().attr(key).set(rpcResponse);
            channelHandlerContext.channel().close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            log.error("不期望错误：",cause);
            super.exceptionCaught(ctx, cause);
        }
    }
}
