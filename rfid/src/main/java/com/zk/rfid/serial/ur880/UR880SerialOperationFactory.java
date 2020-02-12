package com.zk.rfid.serial.ur880;

import com.zk.rfid.bean.DeviceInformation;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UR880SerialOperationFactory {
    private volatile static UR880SerialOperationFactory instance;
    public static final String TAG = UR880SerialOperationFactory.class.getName();

    private Map<Object, Object> ur880SerialOperationMap;
    private Map<Object, Object> ur880SerialOperationMapTemp;

    private UR880SerialOperationFactory() { }

    public static UR880SerialOperationFactory getInstance() {
        if (instance == null) {
            synchronized (UR880SerialOperationFactory.class) {
                if (instance == null)
                    instance = new UR880SerialOperationFactory();
            }
        }
        return instance;
    }

    public boolean init(List<DeviceInformation> deviceInformationList){
        if (ur880SerialOperationMap == null) return false;
        ur880SerialOperationMap = Collections.synchronizedMap(new HashMap<>());
        ur880SerialOperationMapTemp = Collections.synchronizedMap(new HashMap<>());
        for (int i = 0; i < deviceInformationList.size(); i++){
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

}
