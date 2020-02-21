package com.zk.rfid.callback;

import com.zk.rfid.bean.LabelInfo;

public interface InventoryListener {

    void startInventory(int resultCode);

    void endInventory(int resultCode);

    void inventoryValue(LabelInfo labelInfo);

    void cancel(int result, int code);
}
