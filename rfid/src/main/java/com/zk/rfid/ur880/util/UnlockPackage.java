package com.zk.rfid.ur880.util;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.bean.DeviceInformation;

import io.netty.channel.Channel;

public class UnlockPackage {

    public DeviceInformation registeredR(Channel channel, byte[] buffer) {
        char[] deviceIdChar = new char[4];
        for (int i = 7; i < 10; i++) {
            deviceIdChar[i - 7] = (char) buffer[i];
        }
        StringBuilder deviceIdStr = new StringBuilder();
        deviceIdStr.append(deviceIdChar);
        String deviceId = deviceIdStr.toString();

        char[] deviceVersionChar = new char[4];
        for (int i = 7; i < 10; i++) {
            deviceVersionChar[i - 7] = (char) buffer[i];
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
