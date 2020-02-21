package com.zk.rfid.ur880.util;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.bean.LabelInfo;
import com.zk.rfid.callback.DeviceInformationListener;
import com.zk.rfid.callback.InventoryListener;

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

    public void getInventoryH(List<InventoryListener> mInventoryListeners, byte[] buffer) {
        for (InventoryListener inventoryListener : mInventoryListeners) {
            if (buffer[10] == 0x00) {
                inventoryListener.startInventory(buffer[14]);
            } else if (buffer[10] == 0x01) {
                inventoryListener.endInventory(buffer[14]);
            }
        }
    }

    public void getCancelH(List<InventoryListener> mInventoryListeners, byte[] buffer) {
        for (InventoryListener inventoryListener : mInventoryListeners) {
            inventoryListener.cancel(buffer[10], buffer[14]);
        }
    }

    public void getInventoryReportDataH(List<InventoryListener> mInventoryListeners, byte[] buffer) {
        LabelInfo labelInfo = new LabelInfo();

        byte[] deviceIdByte = new byte[4];
        System.arraycopy(buffer, 11, deviceIdByte, 0, 4);
        String deviceId = String.valueOf(Utils.byteArrayToInt(deviceIdByte, 4));
        labelInfo.setDeviceID(deviceId);
        labelInfo.setAntennaNumber(buffer[16]);
        labelInfo.setFastID(buffer[18]);
        int rssiInt = buffer[19] * 256 + buffer[20];
        labelInfo.setRSSI((rssiInt - 65536) / 10);
        String epcTempStr;
        StringBuilder epcStr = new StringBuilder();
        int epcLength = buffer[29] * 256 + buffer[30];
        for (int i = 31; i < (31 + epcLength); i++) {
            epcTempStr = Integer.toHexString(buffer[i] & 0xff);
            if (epcTempStr.length() < 2)
                epcTempStr = "0" + epcTempStr;
            epcStr.append(epcTempStr);
        }
        LogUtil.Companion.getInstance().d("EPC:" + epcStr.toString());
        labelInfo.setEPC(epcStr.toString());
        if (buffer.length >= (31 + epcLength + 12 + 3)) {
            String tidTempStr;
            StringBuilder tidStr = new StringBuilder();
            for (int i = (31 + epcLength); i < (31 + epcLength + 12); i++) {
                tidTempStr = Integer.toHexString(buffer[i] & 0xff);
                if (tidTempStr.length() < 2)
                    tidTempStr = "0" + tidTempStr;
                tidStr.append(tidTempStr);
            }
            labelInfo.setTID(tidStr.toString());
        } else {
            LogUtil.Companion.getInstance().d("TID == null");
            labelInfo.setTID(null);
        }
        for (InventoryListener inventoryListener : mInventoryListeners) {
            inventoryListener.inventoryValue(labelInfo);
        }
    }
}
