package com.zk.rfid.ur880.util;

import com.zk.rfid.ur880.util.Utils.TYPE;
import com.zk.common.utils.LogUtil;

import java.util.Calendar;

public class GroupPackage {

    public byte[] registeredH(int frameNumber) {
        byte[] data;
        data = new byte[14];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.REGISTERED_H.type;
        data[7] = (byte) 0;
        data[8] = (byte) 0;
        data[9] = (byte) 0;
        data[10] = (byte) 0;
        data[11] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 注册 转译前 registeredH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] heartbeatR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.HEART_BEAT_R.type;
        data[7] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 心跳 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] getVersionInfoR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.GET_VERSION_INFO_R.type;
        data[7] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 获取版本号 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] inventoryR(int frameNumber, int fastID, int antennaNumber, int inventoryType) {
        byte[] data;
        data = new byte[18];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.INVENTORY_R.type;
        data[7] = 0x00;
        data[8] = 0x00;
        data[9] = 0x00;
        data[10] = 0x00;
        data[11] = 0x00;
        data[12] = (byte) fastID;
        data[13] = (byte) antennaNumber;
        data[14] = (byte) inventoryType;
        data[15] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 盘点 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] cancelR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.CANCEL_R.type;
        data[7] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] setAntennaConfigurationR(int frameNumber,
                             int antennaEnableZero, int antennaEnableOne, int antennaEnableTwo, int antennaEnableThree,
                             int antennaPowerZero, int antennaPowerOne, int antennaPowerTwo, int antennaPowerThree,
                             int dwellTimeZero, int dwellTimeOne, int dwellTimeTwo, int dwellTimeThree,
                             int calendarCycleZero, int calendarCycleOne, int calendarCycleTwo, int calendarCycleThree) {
        byte[] data;
        data = new byte[26];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.SET_ANTENNA_CONFIGURATION_R.type;

        data[7] = (byte) antennaEnableZero;
        data[8] = (byte) antennaEnableOne;
        data[9] = (byte) antennaEnableTwo;
        data[10] = (byte) antennaEnableThree;

        data[11] = (byte) antennaPowerZero;
        data[12] = (byte) antennaPowerOne;
        data[13] = (byte) antennaPowerTwo;
        data[14] = (byte) antennaPowerThree;

        data[15] = (byte) dwellTimeZero;
        data[16] = (byte) dwellTimeOne;
        data[17] = (byte) dwellTimeTwo;
        data[18] = (byte) dwellTimeThree;

        data[19] = (byte) calendarCycleZero;
        data[20] = (byte) calendarCycleOne;
        data[21] = (byte) calendarCycleTwo;
        data[22] = (byte) calendarCycleThree;

        data[23] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.15 天线配置 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] getAntennaConfigurationR(int frameNumber) {
        byte[] data;
        data = new byte[14];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.GET_ANTENNA_CONFIGURATION_R.type;

        data[7] = 0x00;
        data[8] = 0x00;
        data[9] = 0x00;
        data[10] = 0x00;

        data[11] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.17 天线配置查询命令 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] getAntennaStandingWaveRatioR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.GET_ANTENNA_STANDING_WAVE_RADIO_R.type;

        data[7] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.27 获取天线驻波比 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] setGPOOutputStatusR(int frameNumber, int portNumber, int electricityLevel) {
        byte[] data;
        data = new byte[14];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.SET_GPO_OUTPUT_STATUS_R.type;

        data[7] = (byte) portNumber;
        data[8] = (byte) electricityLevel;
        data[9] = 0x00;
        data[10] = 0x00;

        data[11] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.29 设置GPO输出状态 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] getGPIOutputStatusR(int frameNumber) {
        byte[] data;
        data = new byte[14];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.GET_GPI_OUTPUT_STATUS_R.type;

        data[7] = 0x00;
        data[8] = 0x00;
        data[9] = 0x00;
        data[10] = 0x00;

        data[11] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.31 读取GPI输入命令 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] setBuzzerStatusSettingR(int frameNumber, int buzzerStatus) {
        byte[] data;
        data = new byte[11];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.SET_BUZZER_STATUS_SETTING_R.type;

        data[7] = (byte) buzzerStatus;

        data[8] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.37 蜂鸣器状态设置 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] getBuzzerStatusSettingR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.GET_BUZZER_STATUS_SETTING_R.type;

        data[7] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.39 读写器蜂鸣器状态获取命令 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] timeSynchronizationR(int frameNumber) {
        byte[] data;
        data = new byte[16];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.TIME_SYNCHRONIZATION_R.type;
        Calendar calendar = Calendar.getInstance();
        data[7] = (byte) calendar.get(Calendar.YEAR);
        data[8] = (byte) (calendar.get(Calendar.MONTH) + 1);
        data[9] = (byte) calendar.get(Calendar.DAY_OF_MONTH);
        data[10] = (byte) calendar.get(Calendar.HOUR_OF_DAY);
        data[11] = (byte) calendar.get(Calendar.MINUTE);
        data[12] = (byte) calendar.get(Calendar.SECOND);
        data[13] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.41 时间同步 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public byte[] deviceRestartR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.DEVICE_RESTART_R.type;
        data[7] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.43 设备重启 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

}
