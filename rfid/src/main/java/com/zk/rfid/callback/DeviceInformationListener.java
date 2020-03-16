package com.zk.rfid.callback;

public interface DeviceInformationListener {
    /**
     * 设备注册
     * @param deviceID             设备ID
     * @param deviceVersionNumber  设备软件版本号
     * @param deviceRemoteAddress  设备IP地址（TCP通信独有）
     */
    void registered(String deviceID, String deviceVersionNumber, String deviceRemoteAddress);

    /**
     * 设备心跳
     * @param deviceID   设备ID（只有TCP通信才有心跳）
     */
    void heartbeat(String deviceID);

    void versionInformation(String hardwareVersionNumber, String softwareVersionNumber, String firmwareVersionNumber);

    /**
     * 设备离线
     * @param deviceID   设备ID（只有TCP通信才有设备离线）
     */
    void removed(String deviceID);
}
