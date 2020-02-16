package com.zk.rfid.serial.ur880;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.serial.SerialHelper;
import com.zk.rfid.ur880.util.GroupPackage;
import com.zk.rfid.ur880.util.UnlockPackage;
import com.zk.rfid.ur880.util.Utils;

import java.io.IOException;
import java.security.InvalidParameterException;

public class UR880SerialOperation extends SerialHelper {
    public static final String TAG = UR880SerialOperation.class.getName();
    private boolean isConnect = false;
    private String mID;
    private String mIDTemp;
    private byte[] remainBuffer = null;//上次解析剩余的数据
    private GroupPackage mGroupPackage = new GroupPackage();
    private UnlockPackage mUnlockPackage = new UnlockPackage();

    UR880SerialOperation(String idTemp, String deviceSerialPath, String deviceSerialBaudRate){
        this.mIDTemp = idTemp;
        setPort(deviceSerialPath);
        setBaudRate(deviceSerialBaudRate);
    }

    private RegisteredListener mRegisteredListener;
    interface RegisteredListener{
        void registered(String id, String idTemp);
    }
    void onRegisteredListener(RegisteredListener registeredListener){
        this.mRegisteredListener = registeredListener;
    }


//    public void send(LightSendInfo lightSendInfo) {
//        switch (lightSendInfo.getCommunicationType()) {
//            case 0x07:
//                addSendTask(LightGroupPackage.openLight(lightSendInfo.getTargetAddress(), lightSendInfo.getSourceAddress(),
//                        lightSendInfo.getLightNumber()));
//                break;
//        }
//    }

    //打开串口
    public void openComPort() {
        try {
            open();
        } catch (SecurityException e) {
            LogUtil.Companion.getInstance().d("打开串口失败:没有串口读/写权限!");
        } catch (IOException e) {
            LogUtil.Companion.getInstance().d("打开串口失败:未知错误!");
        } catch (InvalidParameterException e) {
            LogUtil.Companion.getInstance().d("打开串口失败:参数错误!");
        }
        isConnect = true;
        LogUtil.Companion.getInstance().d("打开串口");
    }

    //关闭串口
    public void closeComPort() {
        isConnect = false;
        stopSend();
        close();

    }


    @Override
    protected void onDataReceived(String sPort, byte[] buffer, int size) {
        LogUtil.Companion.getInstance().d(TAG, buffer, buffer.length);
        byte[] tempBytes;
        //如果上次解析有剩余，则将其加上
        if (remainBuffer != null && remainBuffer.length != 0) {
            tempBytes = Utils.bytesMerger(remainBuffer, buffer);
        } else {
            tempBytes = buffer;
        }
        remainBuffer = interceptionReceivedData(tempBytes);
        if (remainBuffer != null) {
            LogUtil.Companion.getInstance().d("上次解析有剩余：", remainBuffer, remainBuffer.length);
        } else
            LogUtil.Companion.getInstance().d("上次解析没有有剩余");

    }

    /**
     * 截取完整的帧
     *
     * @param dataBytes 加上上次剩余的帧后的数据
     * @return 返回截取剩剩余的帧
     */
    private byte[] interceptionReceivedData(byte[] dataBytes) {
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
                checkReceived(tempCompleteBytes, tailPosition2 - headPosition1 + 1);
                //如果一组数据中有多个帧，则将剩余的数据发送
                if (size > (tailPosition2 + 1)) {
                    byte[] subTempBytes = new byte[size - tailPosition2 - 1];
                    System.arraycopy(dataBytes, tailPosition2 + 1, subTempBytes, 0, subTempBytes.length);
                    return interceptionReceivedData(subTempBytes);
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
    public void checkReceived(byte[] buffer, int size) {
        LogUtil.Companion.getInstance().d("serial test Received checkReceived：", buffer, size);

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
                        parser(tBuffer, size - T);
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
    private void parser(byte[] buffer, int size) {
        LogUtil.Companion.getInstance().d("serial test Received", buffer, size);
//        if (buffer[6] == TYPE.REGISTERED_R.getType()) { // 2.1.1	注册帧
//            int id = buffer[7];
//            nettyChannelMap.put(id, channel);
//            LogUtil.getInstance().d("注册-ID：" + id);
//            channel.writeAndFlush(GroupPackage.registeredH(0, buffer[7], 0));
//
//        }
    }
}
