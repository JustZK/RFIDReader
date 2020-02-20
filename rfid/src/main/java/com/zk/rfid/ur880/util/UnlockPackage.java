package com.zk.rfid.ur880.util;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.callback.DeviceInformationListener;

import java.util.List;

import io.netty.channel.Channel;

public class UnlockPackage {

    public DeviceInformation registeredR(Channel channel, byte[] buffer) {
        byte[] deviceIdByte = new byte[4];
        System.arraycopy(buffer, 7, deviceIdByte, 0, 4);
        String deviceId = String.valueOf(Utils.byteArrayToInt(deviceIdByte, 4));
        String deviceVersion = "V";
        deviceVersion = deviceVersion + (buffer[11] & 0xff) + ".";
        deviceVersion = deviceVersion + (buffer[12] & 0xff) + ".";
        deviceVersion = deviceVersion + (buffer[13] & 0xff) + "-20";
        String y = "" + (buffer[14] & 0xff);
        if (y.length() == 1) y = "0" + y;
        String m = "" + (buffer[15] & 0xff);
        if (m.length() == 1) m = "0" + m;
        String d = "" + (buffer[16] & 0xff);
        if (d.length() == 1) d = "0" + d;
        deviceVersion = deviceVersion + y + m + d;

        DeviceInformation deviceInformation = new DeviceInformation();
        deviceInformation.setDeviceID(deviceId);
        deviceInformation.setDeviceVersionNumber(deviceVersion);
        if (channel != null)
            deviceInformation.setDeviceRemoteAddress(channel.remoteAddress().toString());

        return deviceInformation;
    }

    public void getVersionInfoH(List<DeviceInformationListener> deviceInformationListeners, byte[] buffer) {
        for (DeviceInformationListener deviceInformationListener : deviceInformationListeners) {
            deviceInformationListener.versionInformation(String.valueOf(buffer[7]),
                    String.valueOf(buffer[8]), String.valueOf(buffer[9]));
        }
    }
}
