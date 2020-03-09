package com.zk.rfid.serial.ur880;

import com.zk.common.utils.LogUtil;
import com.zk.rfid.bean.DeviceInformation;
import com.zk.rfid.bean.UR880SendInfo;
import com.zk.rfid.callback.InventoryListener;
import com.zk.rfid.callback.DeviceInformationListener;
import com.zk.rfid.callback.FactorySettingListener;
import com.zk.rfid.callback.LabelOperationListener;
import com.zk.rfid.serial.SerialHelper;
import com.zk.rfid.ur880.util.GroupPackage;
import com.zk.rfid.ur880.util.UnlockPackage;
import com.zk.rfid.ur880.util.Utils;
import com.zk.rfid.ur880.util.Utils.TYPE;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UR880SerialOperation extends SerialHelper {
    public static final String TAG = UR880SerialOperation.class.getName();
    private boolean isConnect = false;
    private String mID;
    private String mIDTemp;
    private byte[] remainBuffer = null;//上次解析剩余的数据
    private GroupPackage mGroupPackage = new GroupPackage();
    private UnlockPackage mUnlockPackage = new UnlockPackage();
    private List<InventoryListener> mInventoryListener =
            Collections.synchronizedList(new ArrayList<InventoryListener>());
    private List<DeviceInformationListener> mDeviceInformationListener =
            Collections.synchronizedList(new ArrayList<DeviceInformationListener>());
    private List<FactorySettingListener> mFactorySettingListener =
            Collections.synchronizedList(new ArrayList<FactorySettingListener>());
    private List<LabelOperationListener> mLabelOperationListener =
            Collections.synchronizedList(new ArrayList<LabelOperationListener>());

    UR880SerialOperation(String idTemp, String deviceSerialPath, String deviceSerialBaudRate){
        this.mIDTemp = idTemp;
        setPort(deviceSerialPath);
        setBaudRate(deviceSerialBaudRate);
    }

    private RegisteredListener mRegisteredListener;
    interface RegisteredListener{
        void registered(String id, String idTemp);
    }
    void onRegisteredListener(RegisteredListener registeredListener){
        this.mRegisteredListener = registeredListener;
    }


    void addOnAccessingListener(InventoryListener inventoryListener) {
        mInventoryListener.add(inventoryListener);
    }

    void removeAccessingListener(InventoryListener inventoryListener) {
        mInventoryListener.remove(inventoryListener);
    }

    void removeAllAccessingListener() {
        mInventoryListener.clear();
    }

    void addOnDeviceInformationListener(DeviceInformationListener deviceInformationListener) {
        mDeviceInformationListener.add(deviceInformationListener);
    }

    void removeDeviceInformationListener(DeviceInformationListener deviceInformationListener) {
        mDeviceInformationListener.remove(deviceInformationListener);
    }

    void removeAllDeviceInformationListener() {
        mDeviceInformationListener.clear();
    }

    void addOnFactorySettingListener(FactorySettingListener factorySettingListener) {
        mFactorySettingListener.add(factorySettingListener);
    }

    void removeFactorySettingListener(FactorySettingListener factorySettingListener) {
        mFactorySettingListener.remove(factorySettingListener);
    }

    void removeAllFactorySettingListener() {
        mFactorySettingListener.clear();
    }

    void addOnLabelOperationListener(LabelOperationListener labelOperationListener) {
        mLabelOperationListener.add(labelOperationListener);
    }

    void removeLabelOperationListener(LabelOperationListener labelOperationListener) {
        mLabelOperationListener.remove(labelOperationListener);
    }

    void removeAllLabelOperationListener() {
        mLabelOperationListener.clear();
    }


    void send(UR880SendInfo ur880SendInfo) {

        switch (ur880SendInfo.getCommunicationType()) {
            case REGISTERED_R:
                addSendTask(mGroupPackage.registeredR(0));
                break;
            case GET_VERSION_INFO_R:
                addSendTask(mGroupPackage.getVersionInfoR(0));
                break;
            case INVENTORY_R:
                addSendTask(mGroupPackage.inventoryR(0,
                        ur880SendInfo.getFastId(), ur880SendInfo.getAntennaNumber(),
                        ur880SendInfo.getInventoryType()));
                break;
            case CANCEL_R:
                addSendTask(mGroupPackage.cancelR(0));
                break;
            case SET_ANTENNA_CONFIGURATION_R:
                addSendTask(mGroupPackage.setAntennaConfigurationR(0,
                        ur880SendInfo.getAntennaEnableZero(),
                        ur880SendInfo.getAntennaEnableOne(),
                        ur880SendInfo.getAntennaEnableTwo(),
                        ur880SendInfo.getAntennaEnableThree(),
                        ur880SendInfo.getAntennaPowerZero(),
                        ur880SendInfo.getAntennaPowerOne(),
                        ur880SendInfo.getAntennaPowerTwo(),
                        ur880SendInfo.getAntennaPowerThree(),
                        ur880SendInfo.getDwellTimeZero(),
                        ur880SendInfo.getDwellTimeOne(),
                        ur880SendInfo.getDwellTimeTwo(),
                        ur880SendInfo.getDwellTimeThree(),
                        ur880SendInfo.getCalendarCycleZero(),
                        ur880SendInfo.getCalendarCycleOne(),
                        ur880SendInfo.getCalendarCycleTwo(),
                        ur880SendInfo.getCalendarCycleThree()));
                break;
            case GET_ANTENNA_CONFIGURATION_R:
                addSendTask(mGroupPackage.getAntennaConfigurationR(0));
                break;
            case SET_GPO_OUTPUT_STATUS_R:
                addSendTask(mGroupPackage.setGPOOutputStatusR(0,
                        ur880SendInfo.getPortNumber(), ur880SendInfo.getElectricityLevel()));
                break;
            case GET_GPI_OUTPUT_STATUS_R:
                addSendTask(mGroupPackage.getGPIOutputStatusR(0));
                break;
            case TIME_SYNCHRONIZATION_R:
                addSendTask(mGroupPackage.timeSynchronizationR(0));
                break;
            case DEVICE_RESTART_R:
                addSendTask(mGroupPackage.deviceRestartR(0));
                break;
        }
    }

    //打开串口
    void openComPort() {
        try {
            open();
        } catch (SecurityException e) {
            LogUtil.Companion.getInstance().d("打开串口失败:没有串口读/写权限!");
        } catch (IOException e) {
            LogUtil.Companion.getInstance().d("打开串口失败:未知错误!");
        } catch (InvalidParameterException e) {
            LogUtil.Companion.getInstance().d("打开串口失败:参数错误!");
        }
        isConnect = true;
        LogUtil.Companion.getInstance().d("打开串口");
    }

    //关闭串口
    void closeComPort() {
        isConnect = false;
        stopSend();
        close();

    }


    @Override
    protected void onDataReceived(String sPort, byte[] buffer, int size) {
        LogUtil.Companion.getInstance().d(TAG, buffer, buffer.length);
        byte[] tempBytes;
        //如果上次解析有剩余，则将其加上
        if (remainBuffer != null && remainBuffer.length != 0) {
            tempBytes = Utils.bytesMerger(remainBuffer, buffer);
        } else {
            tempBytes = buffer;
        }
        remainBuffer = interceptionReceivedData(tempBytes);
        if (remainBuffer != null) {
            LogUtil.Companion.getInstance().d("上次解析有剩余：", remainBuffer, remainBuffer.length);
        } else
            LogUtil.Companion.getInstance().d("上次解析没有有剩余");

    }

    /**
     * 截取完整的帧
     *
     * @param dataBytes 加上上次剩余的帧后的数据
     * @return 返回截取剩剩余的帧
     */
    private byte[] interceptionReceivedData(byte[] dataBytes) {
        if (dataBytes == null || dataBytes.length == 0) {
            return null;
        }
        int size = dataBytes.length;
        //针头帧尾的位置
        int headPosition1 = -1, headPosition2 = -1, tailPosition1 = -1, tailPosition2 = -1;
        for (int i = 0; i < size; i++) {
            if (dataBytes[i] == Utils.HEAD_HIGH) {
                if (headPosition1 == -1) {
                    headPosition1 = i;
                } else {
                    if ((i + 1) < size && dataBytes[i + 1] == Utils.HEAD_LOW) {
                        headPosition1 = i;
                    }
                }
            }
            if (dataBytes[i] == Utils.HEAD_LOW) {
                if (headPosition1 == (i - 1)) {
                    headPosition2 = i;
                } else {
//                        headPosition1 = -1;
//                        headPosition2 = -1;
                }
            }
            if (dataBytes[i] == Utils.TAIL_HIGH) {
                if (tailPosition1 == -1) {
                    tailPosition1 = i;
                } else {
                    if ((i + 1) < size && dataBytes[i + 1] == Utils.TAIL_LOW) {
                        tailPosition1 = i;
                    }
                }
            }
            if (dataBytes[i] == Utils.TAIL_LOW) {
                if (tailPosition1 == (i - 1)) {
                    tailPosition2 = i;
                    break;
                } else {
//                        tailPosition1 = -1;
//                        tailPosition2 = -1;
                }
            }
        }
        LogUtil.Companion.getInstance().d("headPosition1:" + headPosition1 + "headPosition2:" + headPosition2 + "tailPosition1:" + tailPosition1 + "tailPosition2:" + tailPosition2);
        if (headPosition1 != -1) {
            if (headPosition2 != -1 && tailPosition1 != -1 && tailPosition2 != -1 && tailPosition1 > headPosition2) {
                byte[] tempCompleteBytes = new byte[tailPosition2 - headPosition1 + 1];
                System.arraycopy(dataBytes, headPosition1, tempCompleteBytes, 0, tailPosition2 - headPosition1 + 1);
                checkReceived(tempCompleteBytes, tailPosition2 - headPosition1 + 1);
                //如果一组数据中有多个帧，则将剩余的数据发送
                if (size > (tailPosition2 + 1)) {
                    byte[] subTempBytes = new byte[size - tailPosition2 - 1];
                    System.arraycopy(dataBytes, tailPosition2 + 1, subTempBytes, 0, subTempBytes.length);
                    return interceptionReceivedData(subTempBytes);
                } else {
                    return null;
                }
            } else if (tailPosition2 == -1) {
                byte[] subTempBytes = new byte[size - headPosition1];
                System.arraycopy(dataBytes, headPosition1, subTempBytes, 0, subTempBytes.length);
                return subTempBytes;
            }
        }
        return null;
    }

    /**
     * 帧校验
     *
     * @param buffer 一条完整的数据帧
     * @param size   改数据帧的长度（加上针头帧尾）
     */
    private void checkReceived(byte[] buffer, int size) {
        LogUtil.Companion.getInstance().d("serial test Received checkReceived：", buffer, size);

        if (buffer[0] == Utils.HEAD_HIGH && buffer[1] == Utils.HEAD_LOW
                && buffer[size - 2] == Utils.TAIL_HIGH && buffer[size - 1] == Utils.TAIL_LOW) {
            int T = Utils.containCheck(buffer, size);
            if (T >= 0) {
                byte[] tBuffer;
                if (T > 0)
                    tBuffer = Utils.translationForUnlock(buffer, size, T);
                else
                    tBuffer = buffer;
                //和校验
                if (Utils.andCheck(tBuffer, size - T)) {
                    //帧长度校验
                    if ((size - Utils.HEAD_TAIL_NUMBER - T) == (tBuffer[2] & 0xff)) {
                        parser(tBuffer, size - T);
                    } else {
                        LogUtil.Companion.getInstance().d("异常：帧长度校验");
                    }

                } else {
                    LogUtil.Companion.getInstance().d("异常：和校验");
                }
            }
        } else {
            LogUtil.Companion.getInstance().d("异常：针头针尾");
        }
    }

    /**
     * 解析 分配
     *
     * @param buffer
     * @param size
     */
    private void parser(byte[] buffer, int size) {
        LogUtil.Companion.getInstance().d("serial test Received", buffer, size);
        if (buffer[6] == TYPE.REGISTERED_H.getType()) {
            DeviceInformation deviceInformation = mUnlockPackage.registeredR(null, buffer);
            mID = deviceInformation.getDeviceID();
            LogUtil.Companion.getInstance().d("注册-ID：" + deviceInformation.getDeviceID());
            if (mRegisteredListener != null){
                mRegisteredListener.registered(deviceInformation.getDeviceID(), mIDTemp);
            }
            for (DeviceInformationListener deviceInformationListener : mDeviceInformationListener){
                deviceInformationListener.registered(deviceInformation.getDeviceID(),
                        deviceInformation.getDeviceVersionNumber(),
                        deviceInformation.getDeviceRemoteAddress());
            }
        } else if (buffer[6] == TYPE.HEART_BEAT_H.getType()) {
            LogUtil.Companion.getInstance().d("心跳");
        } else if (buffer[6] == TYPE.GET_VERSION_INFO_H.getType()) {
            LogUtil.Companion.getInstance().d("获取版本号");
//                mUnlockPackage.getVersionInfoH(mDeviceInformationListener, buffer);
        } else if (buffer[6] == TYPE.INVENTORY_H.getType()) {
            LogUtil.Companion.getInstance().d("Inventory命令帧格式");
            mUnlockPackage.getInventoryH(mInventoryListener, buffer);
        } else if (buffer[6] == TYPE.CANCEL_H.getType()) {
            LogUtil.Companion.getInstance().d("cancel");
            mUnlockPackage.getCancelH(mInventoryListener, buffer);
        } else if (buffer[6] == TYPE.INVENTORY_REPORT_DATA_R.getType()) {
            LogUtil.Companion.getInstance().d("Inventory上报数据");
            mUnlockPackage.getInventoryReportDataH(mInventoryListener, buffer);
        } else if (buffer[6] == TYPE.SET_ANTENNA_CONFIGURATION_H.getType()) {
            LogUtil.Companion.getInstance().d("天线配置");
            mUnlockPackage.setAntennaConfigurationH(mFactorySettingListener, buffer);
        } else if (buffer[6] == TYPE.GET_ANTENNA_CONFIGURATION_H.getType()) {
            LogUtil.Companion.getInstance().d("天线配置查询");
            mUnlockPackage.getAntennaConfigurationH(mFactorySettingListener, buffer);
        } else if (buffer[6] == TYPE.GET_ANTENNA_STANDING_WAVE_RADIO_H.getType()) {
            LogUtil.Companion.getInstance().d("驻波比");
            mUnlockPackage.getAntennaStandingWaveRatioH(mFactorySettingListener, buffer);
        } else if (buffer[6] == TYPE.SET_GPO_OUTPUT_STATUS_H.getType()) {
            LogUtil.Companion.getInstance().d("设置GPO输出状态");
            mUnlockPackage.setGPOOutputStatusH(mFactorySettingListener, buffer);
        } else if (buffer[6] == TYPE.GET_GPI_OUTPUT_STATUS_H.getType()) {
            LogUtil.Companion.getInstance().d("读取GPI输入命令");
            mUnlockPackage.getGPIOutputStatusH(mFactorySettingListener, buffer);
        } else if (buffer[6] == TYPE.SET_BUZZER_STATUS_SETTING_H.getType()) {
            LogUtil.Companion.getInstance().d("蜂鸣器状态设置");
            mUnlockPackage.setBuzzerStatusH(mFactorySettingListener, buffer);
        } else if (buffer[6] == TYPE.GET_BUZZER_STATUS_SETTING_H.getType()) {
            LogUtil.Companion.getInstance().d("读写器蜂鸣器状态获取命令");
            mUnlockPackage.getBuzzerStatusH(mFactorySettingListener, buffer);
        } else if (buffer[6] == TYPE.TIME_SYNCHRONIZATION_H.getType()) {
            LogUtil.Companion.getInstance().d("时间同步");
            mUnlockPackage.timeSynchronizationH(mFactorySettingListener, buffer);
        } else if (buffer[6] == TYPE.DEVICE_RESTART_H.getType()) {
            LogUtil.Companion.getInstance().d("设备重启");
            mUnlockPackage.deviceRestartH(mFactorySettingListener, buffer);
        }
    }
}
