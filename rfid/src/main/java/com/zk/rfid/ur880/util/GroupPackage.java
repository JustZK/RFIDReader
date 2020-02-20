package com.zk.rfid.ur880.util;

import com.zk.rfid.ur880.util.Utils.TYPE;
import com.zk.common.utils.LogUtil;

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

        LogUtil.Companion.getInstance().d("Send 转译前 registeredH：", data, data.length);
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

        LogUtil.Companion.getInstance().d("Send 转译前 heartbeatH：", data, data.length);
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

        LogUtil.Companion.getInstance().d("Send 转译前 heartbeatH：", data, data.length);
        int T = Utils.ifTranslation(data);
        if (T > 0)
            return (Utils.translationForPack(data, data.length, T));
        else
            return data;
    }
}
