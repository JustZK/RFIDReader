package com.zk.rfid.ur880.util;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.bean.LabelInfo;
import com.zk.rfid.callback.DeviceInformationListener;
import com.zk.rfid.callback.FactorySettingListener;
import com.zk.rfid.callback.InventoryListener;

import java.util.Calendar;
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

    public void getInventoryH(List<InventoryListener> inventoryListeners, byte[] buffer) {
        for (InventoryListener inventoryListener : inventoryListeners) {
            if (buffer[10] == 0x00) {
                inventoryListener.startInventory(buffer[14]);
            } else if (buffer[10] == 0x01) {
                inventoryListener.endInventory(buffer[14]);
            }
        }
    }

    public void getCancelH(List<InventoryListener> inventoryListeners, byte[] buffer) {
        for (InventoryListener inventoryListener : inventoryListeners) {
            inventoryListener.cancel(buffer[10], buffer[14]);
        }
    }

    public void getInventoryReportDataH(List<InventoryListener> inventoryListeners, byte[] buffer) {
        LabelInfo labelInfo = new LabelInfo();

        byte[] deviceIdByte = new byte[4];
        System.arraycopy(buffer, 11, deviceIdByte, 0, 4);
        String deviceId = String.valueOf(Utils.byteArrayToInt(deviceIdByte, 4));
        labelInfo.setDeviceID(deviceId);
        labelInfo.setAntennaNumber(buffer[16]);
        labelInfo.setFastID(buffer[18]);
//        int rssiInt = buffer[19] * 256 + buffer[20];
        labelInfo.setRSSI(buffer[19] * 256 + buffer[20]);

        long time = 0;
        time += (buffer[23] < 0 ? 256 + buffer[23] : buffer[23]) * 256 * 256 * 256;
        time += (buffer[24] < 0 ? 256 + buffer[24] : buffer[24]) * 256 * 256;
        time += (buffer[25] < 0 ? 256 + buffer[25] : buffer[25]) * 256;
        time += buffer[26] < 0 ? 256 + buffer[26] : buffer[26];
        time *= 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String timeStr = calendar.get(Calendar.HOUR_OF_DAY) +":" + calendar.get(Calendar.MINUTE)
                + ":" +calendar.get(Calendar.SECOND);
        labelInfo.setOperatingTime(timeStr);
        String epcTempStr;
        StringBuilder epcStr = new StringBuilder();
        int epcLength = buffer[29] * 256 + buffer[30];
        labelInfo.setEPCLength(epcLength);
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
        for (InventoryListener inventoryListener : inventoryListeners) {
            inventoryListener.inventoryValue(labelInfo);
        }
    }

    public void setAntennaConfigurationH(List<FactorySettingListener> factorySettingListeners, byte[] buffer) {
        boolean result = buffer[10] == 0x00;
        int errorNumber = buffer[14];
        for (FactorySettingListener factorySettingListener : factorySettingListeners) {
            factorySettingListener.setAntennaConfigurationResult(result, errorNumber);
        }
    }

    public void getAntennaConfigurationH(List<FactorySettingListener> factorySettingListeners, byte[] buffer) {
        boolean result = buffer[10] == 0x00;
        int errorNumber = buffer[14];
        int antennaEnableZero = buffer[15];
        int antennaEnableOne = buffer[16];
        int antennaEnableTwo = buffer[17];
        int antennaEnableThree = buffer[18];
        int antennaPowerZero = buffer[19];
        int antennaPowerOne = buffer[20];
        int antennaPowerTwo = buffer[21];
        int antennaPowerThree = buffer[22];
        int dwellTimeZero = buffer[23];
        int dwellTimeOne = buffer[24];
        int dwellTimeTwo = buffer[25];
        int dwellTimeThree = buffer[26];
        int calendarCycleZero = buffer[27];
        int calendarCycleOne = buffer[28];
        int calendarCycleTwo = buffer[29];
        int calendarCycleThree = buffer[30];
        for (FactorySettingListener factorySettingListener : factorySettingListeners) {
            factorySettingListener.getAntennaConfigurationResult(result, errorNumber,
                    antennaEnableZero, antennaEnableOne, antennaEnableTwo, antennaEnableThree,
                    antennaPowerZero, antennaPowerOne, antennaPowerTwo, antennaPowerThree,
                    dwellTimeZero, dwellTimeOne, dwellTimeTwo, dwellTimeThree,
                    calendarCycleZero, calendarCycleOne, calendarCycleTwo, calendarCycleThree);
        }
    }

    public void getAntennaStandingWaveRatioH(List<FactorySettingListener> factorySettingListeners, byte[] buffer) {
        float antennaZero = buffer[10] + buffer[9] * 256 + buffer[8] * 256 * 256 + buffer[7] *256 * 256;
        float antennaOne = buffer[14] + buffer[13] * 256 + buffer[12] * 256 * 256 + buffer[11] *256 * 256;
        float antennaTwo = buffer[18] + buffer[17] * 256 + buffer[16] * 256 * 256 + buffer[15] *256 * 256;
        float antennaThree = buffer[22] + buffer[21] * 256 + buffer[20] * 256 * 256 + buffer[19] *256 * 256;
        for (FactorySettingListener factorySettingListener : factorySettingListeners) {
            factorySettingListener.getAntennaStandingWaveRatioResult(antennaZero, antennaOne, antennaTwo, antennaThree);
        }
    }

    public void setGPOOutputStatusH(List<FactorySettingListener> factorySettingListeners, byte[] buffer) {
        boolean result = buffer[10] == 0x00;
        int errorNumber = buffer[14];
        for (FactorySettingListener factorySettingListener : factorySettingListeners) {
            factorySettingListener.setGPOOutputStatusResult(result, errorNumber);
        }
    }

    public void getGPIOutputStatusH(List<FactorySettingListener> factorySettingListeners, byte[] buffer) {
        boolean result = buffer[10] == 0x00;
        int errorNumber = buffer[14];
        int portZeroStatus = buffer[15];
        int portOneStatus = buffer[16];
        int portTwoStatus = buffer[17];
        int portThreeStatus = buffer[18];
        for (FactorySettingListener factorySettingListener : factorySettingListeners) {
            factorySettingListener.getGPIOutputStatusResult(result, errorNumber,
                    portZeroStatus, portOneStatus, portTwoStatus, portThreeStatus);
        }
    }

    public void setBuzzerStatusH(List<FactorySettingListener> factorySettingListeners, byte[] buffer) {
        int errorNumber = buffer[10];
        for (FactorySettingListener factorySettingListener : factorySettingListeners) {
            factorySettingListener.setBuzzerStatusResult(errorNumber);
        }
    }

    public void getBuzzerStatusH(List<FactorySettingListener> factorySettingListeners, byte[] buffer) {
        int buzzerStatus = buffer[7];
        for (FactorySettingListener factorySettingListener : factorySettingListeners) {
            factorySettingListener.getBuzzerStatusResult(buzzerStatus);
        }
    }

    public void timeSynchronizationH(List<FactorySettingListener> factorySettingListeners, byte[] buffer) {
        int errorNumber = buffer[10];
        for (FactorySettingListener factorySettingListener : factorySettingListeners) {
            factorySettingListener.timeSynchronizationResult(errorNumber);
        }
    }

    public void deviceRestartH(List<FactorySettingListener> factorySettingListeners, byte[] buffer) {
        int errorNumber = buffer[10];
        for (FactorySettingListener factorySettingListener : factorySettingListeners) {
            factorySettingListener.deviceRestartResult(errorNumber);
        }
    }
}
