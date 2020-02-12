package com.zk.rfid.netty.server;

import com.zk.common.utils.LogUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler<Object> {
    private static final String TAG = "NettyServerHandler";
    private NettyServerHandler.NettyServerEventProcessor nettyServerEventProcessor;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        LogUtil.Companion.getInstance().d(TAG, "Client ：" + channel.remoteAddress() + "  在线，开始通信");
        if (nettyServerEventProcessor != null) nettyServerEventProcessor.onChannelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        LogUtil.Companion.getInstance().d(TAG, "Client ：" + channel.remoteAddress() + "  离线");
        if (nettyServerEventProcessor != null) nettyServerEventProcessor.onChannelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        Channel channel = ctx.channel();
        LogUtil.Companion.getInstance().d(TAG, "Client ：" + channel.remoteAddress() + "  异常", true);
        LogUtil.Companion.getInstance().d(TAG, "exceptionCaught" + cause.getLocalizedMessage(), true);
        if (nettyServerEventProcessor != null) nettyServerEventProcessor.onExceptionCaught(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        LogUtil.Companion.getInstance().d(TAG, "handlerAdded 信道添加");
        if (nettyServerEventProcessor != null) nettyServerEventProcessor.onHandlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        LogUtil.Companion.getInstance().d(TAG, "handlerRemoved 离开");
        if (nettyServerEventProcessor != null) nettyServerEventProcessor.onHandlerRemoved(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] b = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(b);
        if (nettyServerEventProcessor != null)
            nettyServerEventProcessor.onMessageReceived(ctx, b);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        LogUtil.Companion.getInstance().d(TAG, "messageReceived");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        // TODO 长时间未交互需要优化
    }

    public NettyServerHandler.NettyServerEventProcessor getNettyServerEventProcessor() {
        return nettyServerEventProcessor;
    }

    public void setNettyServerEventProcessor(NettyServerHandler.NettyServerEventProcessor nettyServerEventProcessor) {
        this.nettyServerEventProcessor = nettyServerEventProcessor;
    }

    public static abstract class NettyServerEventProcessor {

        protected void onChannelActive(ChannelHandlerContext ctx) {
        }

        protected void onChannelInactive(ChannelHandlerContext ctx) {
        }

        protected void onExceptionCaught(ChannelHandlerContext ctx) {
        }

        protected void onHandlerAdded(ChannelHandlerContext ctx) {
        }

        protected void onHandlerRemoved(ChannelHandlerContext ctx) {
        }

        protected abstract void onMessageReceived(ChannelHandlerContext ctx, byte[] buffer);

    }
}
