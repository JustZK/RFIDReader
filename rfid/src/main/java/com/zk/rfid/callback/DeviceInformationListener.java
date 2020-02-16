package com.zk.rfid.callback;

public interface DeviceInformationListener {
    void registered(String deviceID, String deviceVersionNumber, String deviceRemoteAddress);

    void heartbeat(String deviceID);

    void versionInformation(String hardwareVersionNumber, String softwareVersionNumber, String firmwareVersionNumber);
}
