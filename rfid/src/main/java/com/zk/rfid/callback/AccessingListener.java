package com.zk.rfid.callback;

import com.zk.rfid.bean.LabelInfo;

import java.util.ArrayList;

public interface AccessingListener {

    void openBoxResult(int controlBoardID, int result);

    void checkBoxDoorState(int controlBoardID, int doorLockStatus);

    void openLightResult(int controlBoardID, int result);

    void accessingResult(ArrayList<LabelInfo> labelInfoList);

    void abort(int abortResult);

    /**
     * 设备异常
     * @param errorCode 1：设备离线 2: 盘点超时
     */
    void error(int controlBoardID, int errorCode);

}
