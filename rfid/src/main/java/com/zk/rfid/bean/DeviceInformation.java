package com.zk.rfid.bean;

import androidx.annotation.Nullable;

public class DeviceInformation {
    private String mDeviceID;
    private String mDeviceVersionNumber;
    private String mDeviceRemoteAddress;
    private String mDeviceSerialPath; //Serial地址
    private String mDeviceSerialBaudRate; //Serial波特率
    private String mHardwareVersionNumber; //硬件版本号
    private String mSoftwareVersionNumber; //软件版本号
    private String mFirmwareVersionNumber; //固件版本号

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj != null && obj.getClass() == DeviceInformation.class) {
            DeviceInformation r = (DeviceInformation) obj;
            return this.mDeviceID.equals(r.mDeviceID);
        }
        return false;

    }

    public String getDeviceID() {
        return mDeviceID;
    }

    public void setDeviceID(String deviceID) {
        mDeviceID = deviceID;
    }

    public String getDeviceVersionNumber() {
        return mDeviceVersionNumber;
    }

    public void setDeviceVersionNumber(String deviceVersionNumber) {
        mDeviceVersionNumber = deviceVersionNumber;
    }

    public String getDeviceRemoteAddress() {
        return mDeviceRemoteAddress;
    }

    public void setDeviceRemoteAddress(String deviceRemoteAddress) {
        mDeviceRemoteAddress = deviceRemoteAddress;
    }

    public String getDeviceSerialPath() {
        return mDeviceSerialPath;
    }

    public void setDeviceSerialPath(String deviceSerialPath) {
        mDeviceSerialPath = deviceSerialPath;
    }

    public String getDeviceSerialBaudRate() {
        return mDeviceSerialBaudRate;
    }

    public void setDeviceSerialBaudRate(String deviceSerialBaudRate) {
        mDeviceSerialBaudRate = deviceSerialBaudRate;
    }

    public String getHardwareVersionNumber() {
        return mHardwareVersionNumber;
    }

    public void setHardwareVersionNumber(String hardwareVersionNumber) {
        mHardwareVersionNumber = hardwareVersionNumber;
    }

    public String getSoftwareVersionNumber() {
        return mSoftwareVersionNumber;
    }

    public void setSoftwareVersionNumber(String softwareVersionNumber) {
        mSoftwareVersionNumber = softwareVersionNumber;
    }

    public String getFirmwareVersionNumber() {
        return mFirmwareVersionNumber;
    }

    public void setFirmwareVersionNumber(String firmwareVersionNumber) {
        mFirmwareVersionNumber = firmwareVersionNumber;
    }
}
