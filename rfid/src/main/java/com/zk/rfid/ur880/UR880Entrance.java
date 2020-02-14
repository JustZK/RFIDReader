package com.zk.rfid.ur880;

import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.netty.server.ur880.UR880ServerParsingLibrary;
import com.zk.rfid.serial.ur880.UR880SerialOperationFactory;

import java.util.List;
import java.util.Map;

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

//    public void disInit(){
//        mConnectionType = null;
//        UR880ServerParsingLibrary.getInstance().
//    }


    public int getConnectionType() {
        return mConnectionType;
    }
}
