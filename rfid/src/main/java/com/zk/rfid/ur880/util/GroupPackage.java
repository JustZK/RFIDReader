package com.zk.rfid.ur880.util;

import com.zk.rfid.ur880.util.Utils.TYPE;
import com.zk.common.utils.LogUtil;

public class GroupPackage {

    public static byte[] registeredH(int frameNumber, byte id, int registerResult) {
        byte[] data;
        data = new byte[12];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.REGISTERED_H.type;
        data[7] = id;
        data[8] = (byte) registerResult;
        data[9] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 转译前 registeredH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public static byte[] heartbeatH(int frameNumber, byte id) {
        byte[] data;
        data = new byte[11];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.HEART_BEAT_H.type;
        data[7] = id;
        data[8] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public static byte[] inventoryH(int frameNumber, byte id, int fastId, int inventoryType) {
        byte[] data;
        data = new byte[13];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.INVENTORY_H.type;
        data[7] = id;
        data[8] = (byte) fastId;
        data[9] = (byte) inventoryType;
        data[10] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 转译前 inventoryH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public static byte[] unLockingH(int frameNumber, byte id) {
        byte[] data;
        data = new byte[11];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.UNLOCKING_H.type;
        data[7] = id;
        data[8] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 转译前 unLockingH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public static byte[] lockStatusH(int frameNumber, byte id) {
        byte[] data;
        data = new byte[12];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.LOCK_STATUS_H.type;
        data[7] = id;
        data[8] = (byte) 0x00;
        data[9] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 转译前 lockStatusH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public static byte[] lightControlCommandH(int frameNumber, byte id, int floor, int region, int indicatorSwitch) {
        byte[] data;
        data = new byte[14];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.LIGHT_CONTROL_COMMAND_H.type;
        data[7] = id;
        data[8] = (byte) floor;
        data[9] = (byte) region;
        data[10] = (byte) indicatorSwitch;
        data[11] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 转译前 lightControlCommandH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public static byte[] antennaConfigurationH(int frameNumber, byte id, int antennaPower, int dwellTime, int calendarCycle) {
        byte[] data;
        data = new byte[23];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.ANTENNA_CONFIGURATION_H.type;
        data[7] = id;
        byte[] antennaPowerByte = Utils.intToByteArray(antennaPower, 4);
        data[8] = antennaPowerByte[0];
        data[9] = antennaPowerByte[1];
        data[10] = antennaPowerByte[2];
        data[11] = antennaPowerByte[3];
        byte[] dwellTimeByte = Utils.intToByteArray(dwellTime, 4);
        data[12] = dwellTimeByte[0];
        data[13] = dwellTimeByte[1];
        data[14] = dwellTimeByte[2];
        data[15] = dwellTimeByte[3];
        byte[] calendarCycleByte = Utils.intToByteArray(calendarCycle, 4);
        data[16] = calendarCycleByte[0];
        data[17] = calendarCycleByte[1];
        data[18] = calendarCycleByte[2];
        data[19] = calendarCycleByte[3];
        data[20] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 转译前 antennaConfigurationH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public static byte[] antennaConfigurationQueryH(int frameNumber, byte id) {
        byte[] data;
        data = new byte[11];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.ANTENNA_CONFIGURATION_QUERY_H.type;
        data[7] = id;
        data[8] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 转译前 antennaConfigurationQueryH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public static byte[] obtainingAntennaStandingWaveRatioH(int frameNumber, byte id, int antennaNumber, int antennaPower) {
        byte[] data;
        data = new byte[16];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.OBTAINING_ANTENNA_STANDING_WAVE_RATIO_H.type;
        data[7] = id;
        data[8] = (byte) antennaNumber;
        byte[] antennaPowerByte = Utils.intToByteArray(antennaPower, 4);
        data[9] = antennaPowerByte[0];
        data[10] = antennaPowerByte[1];
        data[11] = antennaPowerByte[2];
        data[12] = antennaPowerByte[3];
        data[13] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 转译前 obtainingAntennaStandingWaveRatioH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public static byte[] tagAlgorithmReadingH(int frameNumber, byte id) {
        byte[] data;
        data = new byte[11];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.TAG_ALGORITHM_READING_H.type;
        data[7] = id;
        data[8] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 转译前 tagAlgorithmReadingH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }

    public static byte[] labelAlgorithmSettingsH(int frameNumber, byte id,
                                                 int session, int singleAlgorithm, int qValue,
                                                 int retries, int flip, int repeatUntilThereNoLabel,
                                                 int minimumQValue, int maximumQValue, int threshold) {
        byte[] data;
        data = new byte[20];
        Utils.initMessage(data, frameNumber);

        data[6] = TYPE.TAG_ALGORITHM_SETTINGS_H.type;
        data[7] = id;
        data[8] = (byte) session;
        data[9] = (byte) singleAlgorithm;
        data[10] = (byte) qValue;
        data[11] = (byte) retries;
        data[12] = (byte) flip;
        data[13] = (byte) repeatUntilThereNoLabel;
        data[14] = (byte) minimumQValue;
        data[15] = (byte) maximumQValue;
        data[16] = (byte) threshold;
        data[17] = Utils.calcCheckBit(data);

        LogUtil.Companion.getInstance().d("Send 转译前 labelAlgorithmSettingsH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }
}
