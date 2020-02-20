package com.zk.rfid.netty.server.ur880;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.bean.UR880SendInfo;
import com.zk.rfid.callback.AccessingListener;
import com.zk.rfid.callback.DeviceInformationListener;
import com.zk.rfid.callback.FactorySettingListener;
import com.zk.rfid.callback.LabelOperationListener;
import com.zk.rfid.netty.server.NettyServerBootstrap;
import com.zk.rfid.netty.server.NettyServerHandler;
import com.zk.rfid.ur880.util.GroupPackage;
import com.zk.rfid.ur880.util.UnlockPackage;
import com.zk.rfid.ur880.util.Utils;
import com.zk.rfid.ur880.util.Utils.TYPE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class UR880ServerParsingLibrary {
    private volatile static UR880ServerParsingLibrary instance;
    public static final String TAG = UR880ServerParsingLibrary.class.getName();

    private Processor processor = null;
    private NettyServerBootstrap nettyServerBootstrap;

    private UR880ServerParsingLibrary() {
    }

    public static UR880ServerParsingLibrary getInstance() {
        if (instance == null) {
            synchronized (UR880ServerParsingLibrary.class) {
                if (instance == null)
                    instance = new UR880ServerParsingLibrary();
            }
        }
        return instance;
    }

    public void init(int port) {
        processor = new Processor();
        nettyServerBootstrap = new NettyServerBootstrap(port, processor);

    }

    public void connect() {
        new Thread() {
            @Override
            public void run() {
                if (nettyServerBootstrap != null) nettyServerBootstrap.connect();
            }
        }.start();
    }

    public boolean send(UR880SendInfo ur880SendInfo) {
        return processor.send(ur880SendInfo);
    }

    public void addOnAccessingListener(AccessingListener accessingListener) {
        processor.addOnAccessingListener(accessingListener);
    }

    public void removeAccessingListener(AccessingListener accessingListener) {
        processor.removeAccessingListener(accessingListener);
    }

    public void removeAllAccessingListener() {
        processor.removeAllAccessingListener();
    }

    public void addOnDeviceInformationListener(DeviceInformationListener deviceInformationListener) {
        processor.addOnDeviceInformationListener(deviceInformationListener);
    }

    public void removeDeviceInformationListener(DeviceInformationListener deviceInformationListener) {
        processor.removeDeviceInformationListener(deviceInformationListener);
    }

    public void removeAllDeviceInformationListener() {
        processor.removeAllDeviceInformationListener();
    }

    public void addOnFactorySettingListener(FactorySettingListener factorySettingListener) {
        processor.addOnFactorySettingListener(factorySettingListener);
    }

    public void removeFactorySettingListener(FactorySettingListener factorySettingListener) {
        processor.removeFactorySettingListener(factorySettingListener);
    }

    public void removeAllFactorySettingListener() {
        processor.removeAllFactorySettingListener();
    }

    public void addOnLabelOperationListener(LabelOperationListener labelOperationListener) {
        processor.addOnLabelOperationListener(labelOperationListener);
    }

    public void removeLabelOperationListener(LabelOperationListener labelOperationListener) {
        processor.removeLabelOperationListener(labelOperationListener);
    }

    public void removeAllLabelOperationListener() {
        processor.removeAllLabelOperationListener();
    }

    public boolean isOnline(int readerIp) {
        return processor.isOnline(readerIp);
    }

    private static class Processor extends NettyServerHandler.NettyServerEventProcessor {
        private int number = 0;
        private HashMap<Channel, byte[]> remainBufferMap = new HashMap<>();//上次解析剩余的数据
        private HashMap<String, Channel> nettyChannelMap = new HashMap<>();
        private List<DeviceInformation> mDeviceInformationList = new ArrayList<>();
        private GroupPackage mGroupPackage = new GroupPackage();
        private UnlockPackage mUnlockPackage = new UnlockPackage();

        private List<AccessingListener> mAccessingListener =
                Collections.synchronizedList(new ArrayList<AccessingListener>());
        private List<DeviceInformationListener> mDeviceInformationListener =
                Collections.synchronizedList(new ArrayList<DeviceInformationListener>());
        private List<FactorySettingListener> mFactorySettingListener =
                Collections.synchronizedList(new ArrayList<FactorySettingListener>());
        private List<LabelOperationListener> mLabelOperationListener =
                Collections.synchronizedList(new ArrayList<LabelOperationListener>());

        void addOnAccessingListener(AccessingListener accessingListener) {
            mAccessingListener.add(accessingListener);
        }

        void removeAccessingListener(AccessingListener accessingListener) {
            mAccessingListener.remove(accessingListener);
        }

        void removeAllAccessingListener() {
            mAccessingListener.clear();
        }

        void addOnDeviceInformationListener(DeviceInformationListener deviceInformationListener) {
            mDeviceInformationListener.add(deviceInformationListener);
        }

        void removeDeviceInformationListener(DeviceInformationListener deviceInformationListener) {
            mDeviceInformationListener.remove(deviceInformationListener);
        }

        void removeAllDeviceInformationListener() {
            mDeviceInformationListener.clear();
        }

        void addOnFactorySettingListener(FactorySettingListener factorySettingListener) {
            mFactorySettingListener.add(factorySettingListener);
        }

        void removeFactorySettingListener(FactorySettingListener factorySettingListener) {
            mFactorySettingListener.remove(factorySettingListener);
        }

        void removeAllFactorySettingListener() {
            mFactorySettingListener.clear();
        }

        void addOnLabelOperationListener(LabelOperationListener labelOperationListener) {
            mLabelOperationListener.add(labelOperationListener);
        }

        void removeLabelOperationListener(LabelOperationListener labelOperationListener) {
            mLabelOperationListener.remove(labelOperationListener);
        }

        void removeAllLabelOperationListener() {
            mLabelOperationListener.clear();
        }

        boolean isOnline(int boxID) {
            return nettyChannelMap.containsKey(boxID);
        }

        boolean send(UR880SendInfo ur880SendInfo) {
            Channel channel = nettyChannelMap.get(ur880SendInfo.getID());
            if (channel == null) return false;
            switch (ur880SendInfo.getCommunicationType()) {
                case GET_VERSION_INFO_R:
//                    channel.writeAndFlush(GroupPackage.inventoryH(0, (byte) nettyServerSendInfo.getID(),
//                            nettyServerSendInfo.getFastId(), nettyServerSendInfo.getInventoryType()));
                    break;
            }
            return true;
        }

        @Override
        protected void onWriteIdle(ChannelHandlerContext ctx) {
            super.onWriteIdle(ctx);
            //长时间未写入-心跳

        }

        @Override
        protected void onReadIdle(ChannelHandlerContext ctx) {
            super.onReadIdle(ctx);
            //长时间未读取信息，关闭通道
            ctx.channel().close();
        }

        @Override
        protected void onAllIdle(ChannelHandlerContext ctx) {
            super.onAllIdle(ctx);
        }

        @Override
        protected void onMessageReceived(ChannelHandlerContext ctx, byte[] buffer) {
            LogUtil.Companion.getInstance().d(TAG, buffer, buffer.length);
            byte[] remainBuffer = remainBufferMap.get(ctx.channel());
            byte[] tempBytes;
            //如果上次解析有剩余，则将其加上
            if (remainBuffer != null && remainBuffer.length != 0) {
                tempBytes = Utils.bytesMerger(remainBuffer, buffer);
            } else {
                tempBytes = buffer;
            }
            remainBufferMap.put(ctx.channel(), null);
            number = 0;
            remainBuffer = interceptionReceivedData(ctx.channel(), tempBytes);
            if (remainBuffer != null) {
                remainBufferMap.put(ctx.channel(), remainBuffer);
                LogUtil.Companion.getInstance().d("上次解析有剩余：", remainBuffer, remainBuffer.length);
            } else
                LogUtil.Companion.getInstance().d("上次解析没有有剩余");
        }

        @Override
        protected void onChannelActive(ChannelHandlerContext ctx) {
            super.onChannelActive(ctx);
            // 在线，开始通信
        }

        @Override
        protected void onChannelInactive(ChannelHandlerContext ctx) {
            super.onChannelInactive(ctx);
            // 离线
            Channel channel = ctx.channel();
            if (nettyChannelMap.containsValue(channel)) {
                for (Map.Entry<String, Channel> entry : nettyChannelMap.entrySet()) {
                    if (channel.equals(entry.getValue())) {
                        LogUtil.Companion.getInstance().d("nettyChannelMap.remove(entry.getKey()) :" + entry.getKey());
//                        if (accessingListener != null){
//                            accessingListener.error(entry.getKey(), 1);
//                        }
                        nettyChannelMap.remove(entry.getKey());
                        break;
                    }
                }
            }
        }

        @Override
        protected void onExceptionCaught(ChannelHandlerContext ctx) {
            super.onExceptionCaught(ctx);
            // 异常
//            Channel channel = ctx.channel();
//            if (nettyChannelMap.containsValue(channel)) {
//                for (Map.Entry<Integer, Channel> entry : nettyChannelMap.entrySet()) {
//                    if (channel.equals(entry.getValue())) {
//                        LogUtil.getInstance().d("nettyChannelMap.remove(entry.getKey()) :" + entry.getKey());
//                        nettyChannelMap.remove(entry.getKey());
//                        break;
//                    }
//                }
//            }
        }

        @Override
        protected void onHandlerAdded(ChannelHandlerContext ctx) {
            super.onHandlerAdded(ctx);
            // 信道添加
        }

        @Override
        protected void onHandlerRemoved(ChannelHandlerContext ctx) {
            super.onHandlerRemoved(ctx);
            // 离开
            Channel channel = ctx.channel();
            if (nettyChannelMap.containsValue(channel)) {
                for (Map.Entry<String, Channel> entry : nettyChannelMap.entrySet()) {
                    if (channel.equals(entry.getValue())) {
                        LogUtil.Companion.getInstance().d("nettyChannelMap.remove(entry.getKey()) :" + entry.getKey());
//                        if (accessingListener != null){
//                            accessingListener.error(entry.getKey(), 1);
//                        }
                        nettyChannelMap.remove(entry.getKey());
                        break;
                    }
                }
            }

        }


        /**
         * 截取完整的帧
         *
         * @param dataBytes 加上上次剩余的帧后的数据
         * @return 返回截取剩剩余的帧
         */
        private byte[] interceptionReceivedData(Channel channel, byte[] dataBytes) {
            LogUtil.Companion.getInstance().d("第 " + number + "次进入");
            if (dataBytes == null || dataBytes.length == 0) {
                return null;
            }
            int size = dataBytes.length;
            //针头帧尾的位置
            int headPosition1 = -1, headPosition2 = -1, tailPosition1 = -1, tailPosition2 = -1;
            for (int i = 0; i < size; i++) {
                if (dataBytes[i] == Utils.HEAD_HIGH) {
                    if (headPosition1 == -1) {
                        headPosition1 = i;
                    } else {
                        if ((i + 1) < size && dataBytes[i + 1] == Utils.HEAD_LOW) {
                            headPosition1 = i;
                        }
                    }
                }
                if (dataBytes[i] == Utils.HEAD_LOW) {
                    if (headPosition1 == (i - 1)) {
                        headPosition2 = i;
                    } else {
//                        headPosition1 = -1;
//                        headPosition2 = -1;
                    }
                }
                if (dataBytes[i] == Utils.TAIL_HIGH) {
                    if (tailPosition1 == -1) {
                        tailPosition1 = i;
                    } else {
                        if ((i + 1) < size && dataBytes[i + 1] == Utils.TAIL_LOW) {
                            tailPosition1 = i;
                        }
                    }
                }
                if (dataBytes[i] == Utils.TAIL_LOW) {
                    if (tailPosition1 == (i - 1)) {
                        tailPosition2 = i;
                        break;
                    } else {
//                        tailPosition1 = -1;
//                        tailPosition2 = -1;
                    }
                }
            }
            LogUtil.Companion.getInstance().d("headPosition1:" + headPosition1 + "headPosition2:" + headPosition2 + "tailPosition1:" + tailPosition1 + "tailPosition2:" + tailPosition2);
            if (headPosition1 != -1) {
                if (headPosition2 != -1 && tailPosition1 != -1 && tailPosition2 != -1 && tailPosition1 > headPosition2) {
                    byte[] tempCompleteBytes = new byte[tailPosition2 - headPosition1 + 1];
                    System.arraycopy(dataBytes, headPosition1, tempCompleteBytes, 0, tailPosition2 - headPosition1 + 1);
                    LogUtil.Companion.getInstance().d("第 " + number + "条完整的数据帧");
                    checkReceived(channel, tempCompleteBytes, tailPosition2 - headPosition1 + 1);
                    //如果一组数据中有多个帧，则将剩余的数据发送
                    if (size > (tailPosition2 + 1)) {
                        byte[] subTempBytes = new byte[size - tailPosition2 - 1];
                        System.arraycopy(dataBytes, tailPosition2 + 1, subTempBytes, 0, subTempBytes.length);
                        number++;
                        return interceptionReceivedData(channel, subTempBytes);
                    } else {
                        return null;
                    }
                } else if (tailPosition2 == -1) {
                    byte[] subTempBytes = new byte[size - headPosition1];
                    System.arraycopy(dataBytes, headPosition1, subTempBytes, 0, subTempBytes.length);
                    return subTempBytes;
                }
            }
            return null;
        }

        /**
         * 帧校验
         *
         * @param buffer 一条完整的数据帧
         * @param size   改数据帧的长度（加上针头帧尾）
         */
        public void checkReceived(Channel channel, byte[] buffer, int size) {
            LogUtil.Companion.getInstance().d("netty test Received checkReceived：", buffer, size);

            if (buffer[0] == Utils.HEAD_HIGH && buffer[1] == Utils.HEAD_LOW
                    && buffer[size - 2] == Utils.TAIL_HIGH && buffer[size - 1] == Utils.TAIL_LOW) {
                int T = Utils.containCheck(buffer, size);
                if (T >= 0) {
                    byte[] tBuffer;
                    if (T > 0)
                        tBuffer = Utils.translationForUnlock(buffer, size, T);
                    else
                        tBuffer = buffer;
                    //和校验
                    if (Utils.andCheck(tBuffer, size - T)) {
                        //帧长度校验
                        if ((size - Utils.HEAD_TAIL_NUMBER - T) == (tBuffer[2] & 0xff)) {
                            parser(channel, tBuffer, size - T);
                        } else {
                            LogUtil.Companion.getInstance().d("异常：帧长度校验");
                        }

                    } else {
                        LogUtil.Companion.getInstance().d("异常：和校验");
                    }
                }
            } else {
                LogUtil.Companion.getInstance().d("异常：针头针尾");
            }
        }

        /**
         * 解析 分配
         *
         * @param buffer
         * @param size
         */
        private void parser(Channel channel, byte[] buffer, int size) {
            LogUtil.Companion.getInstance().d("netty test Received", buffer, size);
            if (buffer[6] == TYPE.REGISTERED_R.getType()) {
                DeviceInformation deviceInformation = mUnlockPackage.registeredR(channel, buffer);
                mDeviceInformationList.add(deviceInformation);
                nettyChannelMap.put(deviceInformation.getDeviceID(), channel);
                LogUtil.Companion.getInstance().d("注册-ID：" + deviceInformation.getDeviceID());
                channel.writeAndFlush(mGroupPackage.registeredH(0));
                for (DeviceInformationListener deviceInformationListener : mDeviceInformationListener){
                    deviceInformationListener.registered(deviceInformation.getDeviceID(),
                            deviceInformation.getDeviceVersionNumber(),
                            deviceInformation.getDeviceRemoteAddress());
                }
            } else if (buffer[6] == TYPE.HEART_BEAT_H.getType()) {
                LogUtil.Companion.getInstance().d("心跳");
                channel.writeAndFlush(mGroupPackage.heartbeatR(0));
            }
        }
    }
}