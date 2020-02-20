package com.zk.rfid.ur880.util;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.bean.DeviceInformation;

import io.netty.channel.Channel;

public class UnlockPackage {

    public DeviceInformation registeredR(Channel channel, byte[] buffer) {
        byte[] deviceIdByte = new byte[4];
        System.arraycopy(buffer, 7, deviceIdByte, 0, 4);
        String deviceId = String.valueOf(Utils.byteArrayToInt(deviceIdByte, 4));
        char[] deviceVersionChar = new char[4];
        for (int i = 11; i < 17; i++) {
            deviceVersionChar[i - 11] = (char) buffer[i];
        }
        StringBuilder deviceVersionStr = new StringBuilder();
        deviceVersionStr.append(deviceVersionChar);
        String deviceVersion = deviceVersionStr.toString();

        DeviceInformation deviceInformation = new DeviceInformation();
        deviceInformation.setDeviceID(deviceId);
        deviceInformation.setDeviceVersionNumber(deviceVersion);
        if (channel != null) deviceInformation.setDeviceRemoteAddress(channel.remoteAddress().toString());

        return deviceInformation;
    }
}
