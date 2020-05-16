package com.zk.rfid.callback;

public interface CabinetInfoListener {

    void unlockResult(int resultCode);

    void turnOnLightResult(int resultCode);

    void getInfraredOrLockState(int[] unlocks, int[] infrared);
}
