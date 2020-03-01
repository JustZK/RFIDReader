package com.zk.rfid.serial.ur880;

import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.bean.UR880SendInfo;
import com.zk.rfid.callback.InventoryListener;
import com.zk.rfid.callback.DeviceInformationListener;
import com.zk.rfid.callback.FactorySettingListener;
import com.zk.rfid.callback.LabelOperationListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UR880SerialOperationFactory {
    private volatile static UR880SerialOperationFactory instance;
    public static final String TAG = UR880SerialOperationFactory.class.getName();

    private Map<String, UR880SerialOperation> ur880SerialOperationMap;
    private Map<String, UR880SerialOperation> ur880SerialOperationMapTemp;

    private UR880SerialOperationFactory() {
    }

    public static UR880SerialOperationFactory getInstance() {
        if (instance == null) {
            synchronized (UR880SerialOperationFactory.class) {
                if (instance == null)
                    instance = new UR880SerialOperationFactory();
            }
        }
        return instance;
    }

    public boolean init(List<DeviceInformation> deviceInformationList) {
        if (ur880SerialOperationMap != null) return false;
        ur880SerialOperationMap = Collections.synchronizedMap(new HashMap<String, UR880SerialOperation>());
        ur880SerialOperationMapTemp = Collections.synchronizedMap(new HashMap<String, UR880SerialOperation>());
        for (int i = 0; i < deviceInformationList.size(); i++) {
            String deviceSerialPath = deviceInformationList.get(i).getDeviceSerialPath();
            String deviceSerialBaudRate = deviceInformationList.get(i).getDeviceSerialBaudRate();
            UR880SerialOperation ur880SerialOperation =
                    new UR880SerialOperation("ZK_" + i, deviceSerialPath, deviceSerialBaudRate);
            ur880SerialOperationMapTemp.put(("ZK_" + i), ur880SerialOperation);
            ur880SerialOperation.onRegisteredListener(new UR880SerialOperation.RegisteredListener() {
                @Override
                public void registered(String id, String idTemp) {
                    ur880SerialOperationMap.put(id, ur880SerialOperationMapTemp.get(idTemp));
                }
            });
            ur880SerialOperation.openComPort();

        }
        return true;
    }

    public void disConnect(){
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.closeComPort();
        }
    }

    public void send(UR880SendInfo ur880SendInfo) {
        ur880SerialOperationMap.get(ur880SendInfo.getID()).send(ur880SendInfo);
    }

    public void addOnAccessingListener(InventoryListener inventoryListener) {
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.addOnAccessingListener(inventoryListener);
        }
    }

    public void removeAccessingListener(InventoryListener inventoryListener) {
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.removeAccessingListener(inventoryListener);
        }
    }

    public void removeAllAccessingListener() {
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.removeAllAccessingListener();
        }
    }

    public void addOnDeviceInformationListener(DeviceInformationListener deviceInformationListener) {
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.addOnDeviceInformationListener(deviceInformationListener);
        }
    }

    public void removeDeviceInformationListener(DeviceInformationListener deviceInformationListener) {
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.removeDeviceInformationListener(deviceInformationListener);
        }
    }

    public void removeAllDeviceInformationListener() {
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.removeAllDeviceInformationListener();
        }
    }

    public void addOnFactorySettingListener(FactorySettingListener factorySettingListener) {
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.addOnFactorySettingListener(factorySettingListener);
        }
    }

    public void removeFactorySettingListener(FactorySettingListener factorySettingListener) {
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.removeFactorySettingListener(factorySettingListener);
        }
    }

    public void removeAllFactorySettingListener() {
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.removeAllFactorySettingListener();
        }
    }

    public void addOnLabelOperationListener(LabelOperationListener labelOperationListener) {
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.addOnLabelOperationListener(labelOperationListener);
        }
    }

    public void removeLabelOperationListener(LabelOperationListener labelOperationListener) {
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.removeLabelOperationListener(labelOperationListener);
        }
    }

    public void removeAllLabelOperationListener() {
        for (UR880SerialOperation ur880SerialOperation : ur880SerialOperationMap.values()) {
            ur880SerialOperation.removeAllLabelOperationListener();
        }
    }

}
