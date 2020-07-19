package com.zk.rfid.callback;

import java.util.ArrayList;

public interface CabinetInfoListener {

    void unlockResult(int resultCode);

    void turnOnLightResult(int resultCode);

    void getInfraredOrLockState(ArrayList<Integer> unlocks, ArrayList<Integer> infrared);
}
