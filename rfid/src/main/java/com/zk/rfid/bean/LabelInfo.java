package com.zk.rfid.bean;

import androidx.annotation.Nullable;

public class LabelInfo {
    private String mDeviceID; // 读写器ID
    private int mAntennaNumber; // 天线号
    private int mFastID; // FastID标志
    private int mRSSI; // RSSI
    private String mOperatingTime; // 操作时间
    private int mEPCLength; // EPC长度
    private String mEPC; // EPC
    private String mTID; // TID

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj != null && obj.getClass() == LabelInfo.class) {
            LabelInfo r = (LabelInfo) obj;
            return this.mEPC.equals(r.mEPC);
        }
        return false;
    }

    public String getDeviceID() {
        return mDeviceID;
    }

    public void setDeviceID(String deviceID) {
        mDeviceID = deviceID;
    }

    public int getAntennaNumber() {
        return mAntennaNumber;
    }

    public void setAntennaNumber(int antennaNumber) {
        mAntennaNumber = antennaNumber;
    }

    public int getFastID() {
        return mFastID;
    }

    public void setFastID(int fastID) {
        mFastID = fastID;
    }

    public int getRSSI() {
        return mRSSI;
    }

    public void setRSSI(int RSSI) {
        mRSSI = RSSI;
    }

    public String getOperatingTime() {
        return mOperatingTime;
    }

    public void setOperatingTime(String operatingTime) {
        mOperatingTime = operatingTime;
    }

    public int getEPCLength() {
        return mEPCLength;
    }

    public void setEPCLength(int EPCLength) {
        mEPCLength = EPCLength;
    }

    public String getEPC() {
        return mEPC;
    }

    public void setEPC(String EPC) {
        mEPC = EPC;
    }

    public String getTID() {
        return mTID;
    }

    public void setTID(String TID) {
        mTID = TID;
    }
}
