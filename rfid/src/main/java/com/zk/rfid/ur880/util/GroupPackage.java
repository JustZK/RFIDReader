package com.zk.rfid.ur880.util;

import com.zk.rfid.ur880.util.Utils.TYPE;
import com.zk.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class GroupPackage {

    public byte[] registeredR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.REGISTERED_R.type;

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 注册 registeredH：", data, data.length);
        return data;
    }

    public byte[] registeredH(int frameNumber) {
        byte[] data;
        data = new byte[14];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.REGISTERED_H.type;
        data[7] = (byte) 0;
        data[8] = (byte) 0;
        data[9] = (byte) 0;
        data[10] = (byte) 0;

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 注册 registeredH：", data, data.length);
        return data;
    }

    public byte[] heartbeatR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.HEART_BEAT_R.type;

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 心跳  heartbeatH：", data, data.length);
        return data;
    }

    public byte[] getVersionInfoR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.GET_VERSION_INFO_R.type;

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 获取版本号 heartbeatH：", data, data.length);
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

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 盘点 heartbeatH：", data, data.length);
        return data;
    }

    public byte[] cancelR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.CANCEL_R.type;

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send heartbeatH：", data, data.length);
        return data;
    }

    public byte[] setAntennaConfigurationR(int frameNumber,
                             int antennaEnableZero, int antennaEnableOne, int antennaEnableTwo, int antennaEnableThree,
                             int antennaPowerZero, int antennaPowerOne, int antennaPowerTwo, int antennaPowerThree,
                             int dwellTimeZero, int dwellTimeOne, int dwellTimeTwo, int dwellTimeThree,
                             int calendarCycleZero, int calendarCycleOne, int calendarCycleTwo, int calendarCycleThree) {
        byte[] data;
        data = new byte[62];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.SET_ANTENNA_CONFIGURATION_R.type;

        data[7] = (byte) antennaEnableZero;
        data[8] = (byte) antennaEnableOne;
        data[9] = (byte) antennaEnableTwo;
        data[10] = (byte) antennaEnableThree;

        byte[] antennaPowerZeroByte = Utils.intToByteArray(antennaPowerZero);
        data[11] = antennaPowerZeroByte[0];
        data[12] = antennaPowerZeroByte[1];
        data[13] = antennaPowerZeroByte[2];
        data[14] = antennaPowerZeroByte[3];
        byte[] antennaPowerOneByte = Utils.intToByteArray(antennaPowerOne);
        data[15] = antennaPowerOneByte[0];
        data[16] = antennaPowerOneByte[1];
        data[17] = antennaPowerOneByte[2];
        data[18] = antennaPowerOneByte[3];
        byte[] antennaPowerTwoByte = Utils.intToByteArray(antennaPowerTwo);
        data[19] = antennaPowerTwoByte[0];
        data[20] = antennaPowerTwoByte[1];
        data[21] = antennaPowerTwoByte[2];
        data[22] = antennaPowerTwoByte[3];
        byte[] antennaPowerThreeByte = Utils.intToByteArray(antennaPowerThree);
        data[23] = antennaPowerThreeByte[0];
        data[24] = antennaPowerThreeByte[1];
        data[25] = antennaPowerThreeByte[2];
        data[26] = antennaPowerThreeByte[3];

        byte[] dwellTimeZeroByte = Utils.intToByteArray(dwellTimeZero);
        data[27] = dwellTimeZeroByte[0];
        data[28] = dwellTimeZeroByte[1];
        data[29] = dwellTimeZeroByte[2];
        data[30] = dwellTimeZeroByte[3];
        byte[] dwellTimeOneByte = Utils.intToByteArray(dwellTimeOne);
        data[31] = dwellTimeOneByte[0];
        data[32] = dwellTimeOneByte[1];
        data[33] = dwellTimeOneByte[2];
        data[34] = dwellTimeOneByte[3];
        byte[] dwellTimeTwoByte = Utils.intToByteArray(dwellTimeTwo);
        data[35] = dwellTimeTwoByte[0];
        data[36] = dwellTimeTwoByte[1];
        data[37] = dwellTimeTwoByte[2];
        data[38] = dwellTimeTwoByte[3];
        byte[] dwellTimeThreeByte = Utils.intToByteArray(dwellTimeThree);
        data[39] = dwellTimeThreeByte[0];
        data[40] = dwellTimeThreeByte[1];
        data[41] = dwellTimeThreeByte[2];
        data[42] = dwellTimeThreeByte[3];

        byte[] calendarCycleZeroByte = Utils.intToByteArray(calendarCycleZero);
        data[43] = calendarCycleZeroByte[0];
        data[44] = calendarCycleZeroByte[1];
        data[45] = calendarCycleZeroByte[2];
        data[46] = calendarCycleZeroByte[3];
        byte[] calendarCycleOneByte = Utils.intToByteArray(calendarCycleOne);
        data[47] = calendarCycleOneByte[0];
        data[48] = calendarCycleOneByte[1];
        data[49] = calendarCycleOneByte[2];
        data[50] = calendarCycleOneByte[3];
        byte[] calendarCycleTwoByte = Utils.intToByteArray(calendarCycleTwo);
        data[51] = calendarCycleTwoByte[0];
        data[52] = calendarCycleTwoByte[1];
        data[53] = calendarCycleTwoByte[2];
        data[54] = calendarCycleTwoByte[3];
        byte[] calendarCycleThreeByte = Utils.intToByteArray(calendarCycleThree);
        data[55] = calendarCycleThreeByte[0];
        data[56] = calendarCycleThreeByte[1];
        data[57] = calendarCycleThreeByte[2];
        data[58] = calendarCycleThreeByte[3];

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.15 天线配置  heartbeatH：", data, data.length);
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

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.17 天线配置查询命令 heartbeatH：", data, data.length);
        return data;
    }

    public byte[] getAntennaStandingWaveRatioR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.GET_ANTENNA_STANDING_WAVE_RADIO_R.type;

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.27 获取天线驻波比 heartbeatH：", data, data.length);
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

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.29 设置GPO输出状态 heartbeatH：", data, data.length);
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

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.31 读取GPI输入命令 heartbeatH：", data, data.length);
        return data;
    }

    public byte[] setBuzzerStatusSettingR(int frameNumber, int buzzerStatus) {
        byte[] data;
        data = new byte[11];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.SET_BUZZER_STATUS_SETTING_R.type;

        data[7] = (byte) buzzerStatus;

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.37 蜂鸣器状态设置 heartbeatH：", data, data.length);
        return data;
    }

    public byte[] getBuzzerStatusSettingR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.GET_BUZZER_STATUS_SETTING_R.type;

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.39 读写器蜂鸣器状态获取命令 heartbeatH：", data, data.length);
        return data;
    }

    public byte[] timeSynchronizationR(int frameNumber) {
        byte[] data;
        data = new byte[16];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.TIME_SYNCHRONIZATION_R.type;
        Calendar calendar = Calendar.getInstance();
        data[7] = (byte) (calendar.get(Calendar.YEAR) % 2000);
        data[8] = (byte) (calendar.get(Calendar.MONTH) + 1);
        data[9] = (byte) calendar.get(Calendar.DAY_OF_MONTH);
        data[10] = (byte) calendar.get(Calendar.HOUR_OF_DAY);
        data[11] = (byte) calendar.get(Calendar.MINUTE);
        data[12] = (byte) calendar.get(Calendar.SECOND);

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.41 时间同步 heartbeatH：", data, data.length);
        return data;
    }

    public byte[] deviceRestartR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.DEVICE_RESTART_R.type;

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.43 设备重启：", data, data.length);
        return data;
    }

    public byte[] openDoorR(int frameNumber, int lockNumber) {
        byte[] data;
        data = new byte[15];
        Utils.initMessage(data, frameNumber);
        data[6] = TYPE.UNLOCK_R.type;

        LogUtil.Companion.getInstance().d("开门："+lockNumber);
        int lockNumberTemp = lockNumber % 8;
        if (lockNumberTemp == 0) lockNumberTemp =8;
        String temp = "1";
        for (int i = 1 ; i < lockNumberTemp; i++){
            temp += "0";
        }
        if (lockNumber <= 8){
            data[7] = 0x00;
            data[8] = 0x00;
            data[9] = 0x00;
            data[10] = (byte) Integer.parseInt(temp,2);
        } else if (lockNumber > 8 && lockNumber <= 16){
            data[7] = 0x00;
            data[8] = 0x00;
            data[10] = 0x00;
            data[9] = (byte) Integer.parseInt(temp,2);
        } else if (lockNumber > 16 && lockNumber <= 24){
            data[7] = 0x00;
            data[9] = 0x00;
            data[10] = 0x00;
            data[8] = (byte) Integer.parseInt(temp,2);
        } else if (lockNumber > 24 && lockNumber <= 32){
            data[8] = 0x00;
            data[9] = 0x00;
            data[10] = 0x00;
            data[7] = (byte) Integer.parseInt(temp,2);
        }

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.45 开锁指令：", data, data.length);
        return data;
    }

    public byte[] turnOnLightR(int frameNumber, int lightLayerNumber, ArrayList<Integer> lightNumber) {
        byte[] data;
        data = new byte[15];
        Utils.initMessage(data, frameNumber);
        data[6] = TYPE.TURN_ON_LIGHT_R.type;
        data[7] = (byte) lightLayerNumber;

        String[] lightNumbers = new String[32];
        for (int i = 0; i < 32; i++) {
            lightNumbers[i] = "0";
        }
        for (int a : lightNumber) {
            int lockNumberTemp = a % 8;
            if (lockNumberTemp == 0) lockNumberTemp = 8;

            if (a <= 8) {
                lightNumbers[lockNumberTemp - 1] = "1";
            } else if (a > 8 && a <= 16) {
                lightNumbers[8 + lockNumberTemp - 1] = "1";
            } else if (a > 16 && a <= 24) {
                lightNumbers[16 + lockNumberTemp - 1] = "1";
            } else if (a > 24 && a <= 32) {
                lightNumbers[24 + lockNumberTemp - 1] = "1";
            }
        }
        String temp = "";
        for (int i = 7; i >= 0; i--) {
            temp += lightNumbers[i];
        }
        data[11] = (byte) Integer.parseInt(temp, 2);
        temp = "";
        for (int i = 15; i >= 8; i--) {
            temp += lightNumbers[i];
        }
        data[10] = (byte) Integer.parseInt(temp, 2);
        temp = "";
        for (int i = 23; i >= 16; i--) {
            temp += lightNumbers[i];
        }
        data[9] = (byte) Integer.parseInt(temp, 2);
        temp = "";
        for (int i = 31; i >= 24; i--) {
            temp += lightNumbers[i];
        }
        data[8] = (byte) Integer.parseInt(temp, 2);

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.47 亮灯指令：", data, data.length);
        return data;
    }

    public byte[] getInfraredOrLockStateR(int frameNumber) {
        byte[] data;
        data = new byte[10];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.GE_INFRARED_OR_LOCK_STATE_R.type;

        int T = Utils.ifTranslation(data);
        if (T > 0) data = Utils.translationForPack(data, data.length, T);
        data[data.length - 3] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 2.50 获取红外状态、锁状态应答：", data, data.length);
        return data;
    }

}
