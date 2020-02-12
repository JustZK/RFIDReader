package com.zk.rfid.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerBootstrap {
    public static final String TAG = "NettyServerBootstrap";
    private int port = 8080;
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private ServerBootstrap bootstrap;
//    private NettyServerHandler nettyServerHandler;

    public NettyServerBootstrap(int port, final NettyServerHandler.NettyServerEventProcessor nettyServerEventProcessor) {
        this.port = port;

//        this.nettyServerHandler = nettyServerHandler;

        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();

        bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1024, 1024 * 32, 1024 * 64))
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new SimpleInitializer(nettyServerEventProcessor));


    }


    private ChannelFuture future = null;

    public void connect() {
        try {
            future = bootstrap.bind(this.port).sync();
            System.out.println("服务器已经启动");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public void disconnect() {
        future.channel().disconnect();
    }

    public void close() {
        try {
            future.channel().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
