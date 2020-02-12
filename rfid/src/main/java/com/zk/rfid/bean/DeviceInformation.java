package com.zk.rfid.bean;

public class DeviceInformation {
    private String mDeviceID;
    private String mDeviceVersionNumber;
    private String mDeviceIp; // IP
    private String mDevicePort; // 端口
    private String mDeviceSerialPath; //Serial地址
    private String mDeviceSerialBaudRate; //Serial波特率


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

    public String getDeviceIp() {
        return mDeviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        mDeviceIp = deviceIp;
    }

    public String getDevicePort() {
        return mDevicePort;
    }

    public void setDevicePort(String devicePort) {
        mDevicePort = devicePort;
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
}
