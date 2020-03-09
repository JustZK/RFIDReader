package com.zk.rfid.ur880;

import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.bean.UR880SendInfo;
import com.zk.rfid.callback.InventoryListener;
import com.zk.rfid.callback.DeviceInformationListener;
import com.zk.rfid.callback.FactorySettingListener;
import com.zk.rfid.callback.LabelOperationListener;
import com.zk.rfid.netty.server.ur880.UR880ServerParsingLibrary;
import com.zk.rfid.serial.ur880.UR880SerialOperationFactory;

import java.util.List;

public class UR880Entrance {
    private volatile static UR880Entrance instance;
    private static final String TAG = UR880Entrance.class.getName();
    private Integer mConnectionType;

    public static final int CONNECTION_TCP_IP = 1;
    public static final int CONNECTION_SERIAL = 2;

    private UR880Entrance() { }
    public static UR880Entrance getInstance() {
        if (instance == null) {
            synchronized (UR880Entrance.class) {
                if (instance == null)
                    instance = new UR880Entrance();
            }
        }
        return instance;
    }

    public boolean init(Integer connectionType, Integer serverPort, List<DeviceInformation> deviceInformationList){
        if (mConnectionType != null) return false;
        this.mConnectionType = connectionType;
        if (connectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().init(serverPort);
            UR880ServerParsingLibrary.getInstance().connect();
        } else if (connectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().init(deviceInformationList);
        }
        return true;
    }

    public void connect(){
        if (mConnectionType == null) return;
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().connect();
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().connect();
        }
    }

    public void disConnect(){
        if (mConnectionType == null) return;
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().disConnect();
            mConnectionType = null;
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().disConnect();
            mConnectionType = null;
        }
    }

    public int getConnectionType() {
        return mConnectionType;
    }

    public void send(UR880SendInfo ur880SendInfo){
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().send(ur880SendInfo);
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().send(ur880SendInfo);
        }
    }

    public void addOnInventoryListener(InventoryListener inventoryListener) {
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().addOnAccessingListener(inventoryListener);
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().addOnAccessingListener(inventoryListener);
        }
    }

    public void removeInventoryListener(InventoryListener inventoryListener) {
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().removeAccessingListener(inventoryListener);
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().removeAccessingListener(inventoryListener);
        }
    }

    public void removeAllInventoryListener() {
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().removeAllAccessingListener();
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().removeAllAccessingListener();
        }
    }

    public void addOnDeviceInformationListener(DeviceInformationListener deviceInformationListener) {
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().addOnDeviceInformationListener(deviceInformationListener);
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().addOnDeviceInformationListener(deviceInformationListener);
        }
    }

    public void removeDeviceInformationListener(DeviceInformationListener deviceInformationListener) {
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().removeDeviceInformationListener(deviceInformationListener);
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().removeDeviceInformationListener(deviceInformationListener);
        }
    }

    public void removeAllDeviceInformationListener() {
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().removeAllDeviceInformationListener();
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().removeAllDeviceInformationListener();
        }
    }

    public void addOnFactorySettingListener(FactorySettingListener factorySettingListener) {
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().addOnFactorySettingListener(factorySettingListener);
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().addOnFactorySettingListener(factorySettingListener);
        }
    }

    public void removeFactorySettingListener(FactorySettingListener factorySettingListener) {
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().removeFactorySettingListener(factorySettingListener);
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().removeFactorySettingListener(factorySettingListener);
        }
    }

    public void removeAllFactorySettingListener() {
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().removeAllFactorySettingListener();
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().removeAllFactorySettingListener();
        }
    }

    public void addOnLabelOperationListener(LabelOperationListener labelOperationListener) {
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().addOnLabelOperationListener(labelOperationListener);
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().addOnLabelOperationListener(labelOperationListener);
        }
    }

    public void removeLabelOperationListener(LabelOperationListener labelOperationListener) {
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().removeLabelOperationListener(labelOperationListener);
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().removeLabelOperationListener(labelOperationListener);
        }
    }

    public void removeAllLabelOperationListener() {
        if (mConnectionType == CONNECTION_TCP_IP){
            UR880ServerParsingLibrary.getInstance().removeAllLabelOperationListener();
        } else if (mConnectionType == CONNECTION_SERIAL){
            UR880SerialOperationFactory.getInstance().removeAllLabelOperationListener();
        }
    }
}
