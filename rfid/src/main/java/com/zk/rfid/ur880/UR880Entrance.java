package com.zk.rfid.ur880;

import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.bean.UR880SendInfo;
import com.zk.rfid.netty.server.ur880.UR880ServerParsingLibrary;
import com.zk.rfid.serial.ur880.UR880SerialOperationFactory;

import java.util.List;

public class UR880Entrance {
    private volatile static UR880Entrance instance;
    public static final String TAG = UR880Entrance.class.getName();
    private Integer mConnectionType;

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
        if (connectionType == 1){
            UR880ServerParsingLibrary.getInstance().init(serverPort);
            UR880ServerParsingLibrary.getInstance().connect();
        } else if (connectionType == 2){
            UR880SerialOperationFactory.getInstance().init(deviceInformationList);
        }
        return true;
    }

    public int getConnectionType() {
        return mConnectionType;
    }

    public void send(UR880SendInfo ur880SendInfo){
        if (mConnectionType == 1){
            UR880ServerParsingLibrary.getInstance().send(ur880SendInfo);
        } else if (mConnectionType == 2){
            UR880SerialOperationFactory.getInstance().send(ur880SendInfo);
        }
    }
}
