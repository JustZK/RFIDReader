package com.zk.rfid.serial.ur880;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.serial.SerialHelper;

import java.io.IOException;
import java.security.InvalidParameterException;

public class UR880SerialOperation extends SerialHelper {
    public static final String TAG = UR880SerialOperation.class.getName();
    private boolean isConnect = false;
    private String mID;
    private String mIDTemp;

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
        LogUtil.Companion.getInstance().d("（灯）onDataReceived---：：：：", buffer, size);

//        if (buffer[0] == DoorUtils.HEAD_HIGH && buffer[1] == DoorUtils.HEAD_LOW) {
//            int needleLength = buffer[4] * 256 + buffer[5];
//            byte[] dataBuffer = new byte[needleLength + 2];
//            System.arraycopy(buffer, 0, dataBuffer, 0, dataBuffer.length);
//            checkReceived(dataBuffer, needleLength + 2);
//        }

    }

    private void checkReceived(byte[] buffer, int size) {
        LogUtil.Companion.getInstance().d("onDataReceived --------", buffer, size);
    }
}
