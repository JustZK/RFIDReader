package com.zk.rfid.bean;

import com.zk.rfid.ur880.util.Utils.TYPE;

import java.lang.reflect.Parameter;
import java.util.ArrayList;

public class UR880SendInfo {
    private TYPE communicationType; //通信类型
    private String ID; //

    private int fastId; //0x01 启用FastID功能 0x00 不启用FastID功能
    private int antennaNumber; //天线号 (0-3)
    private int inventoryType; //0x00 非连续 0x01 连续

    private String accessPassword; //访问密码
    private int bankMemory; //存储区 [0x00:Reserved Memory] [0x01:EPC Memory] [0x02:TID Memory] [0x03:User Memory]
    private int offset; //偏移 从存储区起始位置偏移多少个word(16bits)开始读取数据
    private int readLength; //读取长度
    //天线号
    private int epcLength; //EPC长度
    private String epc; //EPC

    private int dataLength;//数据长度
    private String dataContent;//数据内容

    private int antennaEnableZero; //使能
    private int antennaEnableOne; //使能
    private int antennaEnableTwo; //使能
    private int antennaEnableThree; //使能
    private int antennaPowerZero; //天线功率
    private int antennaPowerOne; //天线功率
    private int antennaPowerTwo; //天线功率
    private int antennaPowerThree; //天线功率
    private int dwellTimeZero; //驻留时间 毫秒
    private int dwellTimeOne; //驻留时间 毫秒
    private int dwellTimeTwo; //驻留时间 毫秒
    private int dwellTimeThree; //驻留时间 毫秒
    private int calendarCycleZero; //盘讯周期 默认是0
    private int calendarCycleOne; //盘讯周期 默认是0
    private int calendarCycleTwo; //盘讯周期 默认是0
    private int calendarCycleThree; //盘讯周期 默认是0

    private int sceneMode; //场景模式：0：自定义模式，只有在自定义模式下，后面的参数配置才生效。 1：多标签群读模式；默认将后续的参数进行配置
    private int sessionSelection;
    private int singleAlgorithm; //单化算法 0x00 固定算法 0x01 动态算法
    private int qValue; //Q值 在配置为动态算法时，表示起始Q值
    private int retries; //重复次数  1次
    private int flip; //翻转 0x00 不翻转 0x01 翻转
    private int repeatUntilThereNoLabel; //重复直至无标签
    private int minimumQValue; //最小Q值 阀值在动态算法下有效 默认是0
    private int maximumQValue; //最大Q值 阀值在动态算法下有效 默认是15
    private int initialQValue; //初始Q值
    private int threshold; //阀值 默认是4
    private int fsa; //FSA
    private int airProfile; //Air profile

    private int portNumber;
    private int electricityLevel;

    private int buzzerStatus; //蜂鸣器状态

    private int lockNumber; //锁号
    private int lightLayerNumber; //灯层号
    private ArrayList<Integer> lightNumbers; //灯号

    public static class Builder {
        UR880SendInfo mUR880SendInfo;

        public Builder() {
            mUR880SendInfo = new UR880SendInfo();
        }

        public Builder register() {
            mUR880SendInfo.setCommunicationType(TYPE.REGISTERED_R);
            return this;
        }

        // 2.5	Host请求读写器软硬件版本信息
        public Builder getVersionInformation(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.GET_VERSION_INFO_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

        // 2.7	Inventory命令帧格式
        public Builder inventory(String ID, int fastId, int antennaNumber, int inventoryType) {
            mUR880SendInfo.setCommunicationType(TYPE.INVENTORY_R);
            mUR880SendInfo.setID(ID);
            mUR880SendInfo.setAntennaNumber(antennaNumber);
            mUR880SendInfo.setFastId(fastId);
            mUR880SendInfo.setInventoryType(inventoryType);
            return this;
        }

        //2.9	Read命令帧格式
        public Builder read(String ID, String accessPassword, int bankMemory, int offset,
                            int readLength, int antennaNumber, int epcLength, String epc) {
            mUR880SendInfo.setCommunicationType(TYPE.INVENTORY_R);
            mUR880SendInfo.setID(ID);
            mUR880SendInfo.setAccessPassword(accessPassword);
            mUR880SendInfo.setBankMemory(bankMemory);
            mUR880SendInfo.setOffset(offset);
            mUR880SendInfo.setReadLength(readLength);
            mUR880SendInfo.setAntennaNumber(antennaNumber);
            mUR880SendInfo.setEpcLength(epcLength);
            mUR880SendInfo.setEpc(epc);
            return this;
        }

        //2.11	Write命令帧格式
        public Builder write(String ID, String accessPassword, int bankMemory, int offset,
                             int dataLength, String dataContent, int antennaNumber, int epcLength, String epc) {
            mUR880SendInfo.setCommunicationType(TYPE.INVENTORY_R);
            mUR880SendInfo.setID(ID);
            mUR880SendInfo.setAccessPassword(accessPassword);
            mUR880SendInfo.setBankMemory(bankMemory);
            mUR880SendInfo.setOffset(offset);
            mUR880SendInfo.setDataLength(dataLength);
            mUR880SendInfo.setDataContent(dataContent);
            mUR880SendInfo.setAntennaNumber(antennaNumber);
            mUR880SendInfo.setEpcLength(epcLength);
            mUR880SendInfo.setEpc(epc);
            return this;
        }

        //2.13	Cancel命令帧
        public Builder cancel(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.CANCEL_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

        //2.15	天线配置 Antenna Configuration
        public Builder setAntennaConfiguration(String ID,
                                               int antennaEnableZero, int antennaEnableOne, int antennaEnableTwo, int antennaEnableThree,
                                               int antennaPowerZero, int antennaPowerOne, int antennaPowerTwo, int antennaPowerThree,
                                               int dwellTimeZero, int dwellTimeOne, int dwellTimeTwo, int dwellTimeThree,
                                               int calendarCycleZero, int calendarCycleOne, int calendarCycleTwo, int calendarCycleThree) {
            mUR880SendInfo.setCommunicationType(TYPE.SET_ANTENNA_CONFIGURATION_R);
            mUR880SendInfo.setID(ID);
            mUR880SendInfo.setAntennaEnableZero(antennaEnableZero);
            mUR880SendInfo.setAntennaEnableOne(antennaEnableOne);
            mUR880SendInfo.setAntennaEnableTwo(antennaEnableTwo);
            mUR880SendInfo.setAntennaEnableThree(antennaEnableThree);
            mUR880SendInfo.setAntennaPowerZero(antennaPowerZero);
            mUR880SendInfo.setAntennaPowerOne(antennaPowerOne);
            mUR880SendInfo.setAntennaPowerTwo(antennaPowerTwo);
            mUR880SendInfo.setAntennaPowerThree(antennaPowerThree);
            mUR880SendInfo.setDwellTimeZero(dwellTimeZero);
            mUR880SendInfo.setDwellTimeOne(dwellTimeOne);
            mUR880SendInfo.setDwellTimeTwo(dwellTimeTwo);
            mUR880SendInfo.setDwellTimeThree(dwellTimeThree);
            mUR880SendInfo.setCalendarCycleZero(calendarCycleZero);
            mUR880SendInfo.setCalendarCycleOne(calendarCycleOne);
            mUR880SendInfo.setCalendarCycleTwo(calendarCycleTwo);
            mUR880SendInfo.setCalendarCycleThree(calendarCycleThree);
            return this;
        }

        //2.17	天线配置查询命令
        public Builder getAntennaConfiguration(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.GET_ANTENNA_CONFIGURATION_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

        //2.19	读写器UHF参数配置
        public Builder setReaderUHFParameterConfigurationForSceneModeZero(String ID,
                                                                          int sessionSelection,
                                                                          int singleAlgorithm,
                                                                          int initialQValue,
                                                                          int retries,
                                                                          int flip,
                                                                          int repeatUntilThereNoLabel,
                                                                          int minimumQValue,
                                                                          int maximumQValue,
                                                                          int threshold,
                                                                          int fsa,
                                                                          int airProfile,
                                                                          int fastId) {
            mUR880SendInfo.setCommunicationType(TYPE.SET_READER_UHF_PARAMETER_CONFIGURATION_R);
            mUR880SendInfo.setID(ID);
            mUR880SendInfo.setSceneMode(0);
            mUR880SendInfo.setSessionSelection(sessionSelection);
            mUR880SendInfo.setSingleAlgorithm(singleAlgorithm);
            mUR880SendInfo.setInitialQValue(initialQValue);
            mUR880SendInfo.setRetries(retries);
            mUR880SendInfo.setFlip(flip);
            mUR880SendInfo.setRepeatUntilThereNoLabel(repeatUntilThereNoLabel);
            mUR880SendInfo.setMinimumQValue(minimumQValue);
            mUR880SendInfo.setMaximumQValue(maximumQValue);
            mUR880SendInfo.setThreshold(threshold);
            mUR880SendInfo.setFsa(fsa);
            mUR880SendInfo.setAirProfile(airProfile);
            mUR880SendInfo.setFastId(fastId);
            return this;
        }

        //2.19	读写器UHF参数配置
        public Builder setReaderUHFParameterConfigurationForSceneModeOne(String ID,
                                                                         int sessionSelection,
                                                                         int singleAlgorithm,
                                                                         int QValue,
                                                                         int retries,
                                                                         int flip,
                                                                         int repeatUntilThereNoLabel,
                                                                         int minimumQValue,
                                                                         int maximumQValue,
                                                                         int initialQValue,
                                                                         int threshold,
                                                                         int fsa,
                                                                         int airProfile) {
            mUR880SendInfo.setCommunicationType(TYPE.SET_READER_UHF_PARAMETER_CONFIGURATION_R);
            mUR880SendInfo.setID(ID);
            mUR880SendInfo.setSceneMode(1);
            mUR880SendInfo.setSessionSelection(sessionSelection);
            mUR880SendInfo.setSingleAlgorithm(singleAlgorithm);
            mUR880SendInfo.setQValue(QValue);
            mUR880SendInfo.setRetries(retries);
            mUR880SendInfo.setFlip(flip);
            mUR880SendInfo.setRepeatUntilThereNoLabel(repeatUntilThereNoLabel);
            mUR880SendInfo.setMinimumQValue(minimumQValue);
            mUR880SendInfo.setMaximumQValue(maximumQValue);
            mUR880SendInfo.setInitialQValue(initialQValue);
            mUR880SendInfo.setThreshold(threshold);
            mUR880SendInfo.setFsa(fsa);
            mUR880SendInfo.setAirProfile(airProfile);
            return this;
        }

        //2.21	读写器UHF参数获取命令
        public Builder getReaderUHFParameterConfiguration(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.GET_READER_UHF_PARAMETER_CONFIGURATION_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

        //2.23	读写器系统参数配置 TODO
        public Builder setReaderSystemParameterConfiguration(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.SET_READER_SYSTEM_PARAMETER_CONFIGURATION_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

        // TODO
        public Builder getReaderSystemParameterConfiguration(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.GET_READER_SYSTEM_PARAMETER_CONFIGURATION_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

        //2.27	获取天线驻波比
        public Builder getAntennaStandingWaveRatio(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.GET_ANTENNA_STANDING_WAVE_RADIO_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

        //2.29	设置GPO输出状态
        public Builder setGPOOutputStatus(String ID, int portNumber, int electricityLevel) {
            mUR880SendInfo.setCommunicationType(TYPE.SET_GPO_OUTPUT_STATUS_R);
            mUR880SendInfo.setID(ID);
            mUR880SendInfo.setPortNumber(portNumber);
            mUR880SendInfo.setElectricityLevel(electricityLevel);
            return this;
        }

        //2.31	读取GPI输入命令
        public Builder getGPIOutputStatus(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.GET_GPI_OUTPUT_STATUS_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

        //2.37	蜂鸣器状态设置 Buzzer status setting
        public Builder setBuzzerStatusSetting(String ID, int buzzerStatus) {
            mUR880SendInfo.setCommunicationType(TYPE.SET_BUZZER_STATUS_SETTING_R);
            mUR880SendInfo.setID(ID);
            mUR880SendInfo.setBuzzerStatus(buzzerStatus);
            return this;
        }

        //2.39	读写器蜂鸣器状态获取命令
        public Builder getBuzzerStatusSetting(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.GET_BUZZER_STATUS_SETTING_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

        //2.41	时间同步 Time synchronization TODO
        public Builder timeSynchronization(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.TIME_SYNCHRONIZATION_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

        //2.43	设备重启 Device restart
        public Builder deviceRestart(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.DEVICE_RESTART_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

        //2.45	开锁指令
        public Builder openDoor(String ID, int lockNumber) {
            mUR880SendInfo.setCommunicationType(TYPE.UNLOCK_R);
            mUR880SendInfo.setID(ID);
            mUR880SendInfo.setLockNumber(lockNumber);
            return this;
        }

        //2.47	亮灯指令
        public Builder turnOnLight(String ID, int lightLayerNumber, ArrayList<Integer> lightNumbers) {
            mUR880SendInfo.setCommunicationType(TYPE.TURN_ON_LIGHT_R);
            mUR880SendInfo.setID(ID);
            mUR880SendInfo.setLightLayerNumber(lightLayerNumber);
            mUR880SendInfo.setLightNumbers(lightNumbers);
            return this;
        }

        //2.49	获取红外状态、锁状态
        public Builder getInfraredOrLockState(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.GE_INFRARED_OR_LOCK_STATE_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

        public UR880SendInfo build() {
            return mUR880SendInfo;
        }
    }

    public TYPE getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(TYPE communicationType) {
        this.communicationType = communicationType;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getFastId() {
        return fastId;
    }

    public void setFastId(int fastId) {
        this.fastId = fastId;
    }

    public int getAntennaNumber() {
        return antennaNumber;
    }

    public void setAntennaNumber(int antennaNumber) {
        this.antennaNumber = antennaNumber;
    }

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getAccessPassword() {
        return accessPassword;
    }

    public void setAccessPassword(String accessPassword) {
        this.accessPassword = accessPassword;
    }

    public int getBankMemory() {
        return bankMemory;
    }

    public void setBankMemory(int bankMemory) {
        this.bankMemory = bankMemory;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getReadLength() {
        return readLength;
    }

    public void setReadLength(int readLength) {
        this.readLength = readLength;
    }

    public int getEpcLength() {
        return epcLength;
    }

    public void setEpcLength(int epcLength) {
        this.epcLength = epcLength;
    }

    public String getEpc() {
        return epc;
    }

    public void setEpc(String epc) {
        this.epc = epc;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public int getAntennaEnableZero() {
        return antennaEnableZero;
    }

    public void setAntennaEnableZero(int antennaEnableZero) {
        this.antennaEnableZero = antennaEnableZero;
    }

    public int getAntennaEnableOne() {
        return antennaEnableOne;
    }

    public void setAntennaEnableOne(int antennaEnableOne) {
        this.antennaEnableOne = antennaEnableOne;
    }

    public int getAntennaEnableTwo() {
        return antennaEnableTwo;
    }

    public void setAntennaEnableTwo(int antennaEnableTwo) {
        this.antennaEnableTwo = antennaEnableTwo;
    }

    public int getAntennaEnableThree() {
        return antennaEnableThree;
    }

    public void setAntennaEnableThree(int antennaEnableThree) {
        this.antennaEnableThree = antennaEnableThree;
    }

    public int getAntennaPowerZero() {
        return antennaPowerZero;
    }

    public void setAntennaPowerZero(int antennaPowerZero) {
        this.antennaPowerZero = antennaPowerZero;
    }

    public int getAntennaPowerOne() {
        return antennaPowerOne;
    }

    public void setAntennaPowerOne(int antennaPowerOne) {
        this.antennaPowerOne = antennaPowerOne;
    }

    public int getAntennaPowerTwo() {
        return antennaPowerTwo;
    }

    public void setAntennaPowerTwo(int antennaPowerTwo) {
        this.antennaPowerTwo = antennaPowerTwo;
    }

    public int getAntennaPowerThree() {
        return antennaPowerThree;
    }

    public void setAntennaPowerThree(int antennaPowerThree) {
        this.antennaPowerThree = antennaPowerThree;
    }

    public int getDwellTimeZero() {
        return dwellTimeZero;
    }

    public void setDwellTimeZero(int dwellTimeZero) {
        this.dwellTimeZero = dwellTimeZero;
    }

    public int getDwellTimeOne() {
        return dwellTimeOne;
    }

    public void setDwellTimeOne(int dwellTimeOne) {
        this.dwellTimeOne = dwellTimeOne;
    }

    public int getDwellTimeTwo() {
        return dwellTimeTwo;
    }

    public void setDwellTimeTwo(int dwellTimeTwo) {
        this.dwellTimeTwo = dwellTimeTwo;
    }

    public int getDwellTimeThree() {
        return dwellTimeThree;
    }

    public void setDwellTimeThree(int dwellTimeThree) {
        this.dwellTimeThree = dwellTimeThree;
    }

    public int getCalendarCycleZero() {
        return calendarCycleZero;
    }

    public void setCalendarCycleZero(int calendarCycleZero) {
        this.calendarCycleZero = calendarCycleZero;
    }

    public int getCalendarCycleOne() {
        return calendarCycleOne;
    }

    public void setCalendarCycleOne(int calendarCycleOne) {
        this.calendarCycleOne = calendarCycleOne;
    }

    public int getCalendarCycleTwo() {
        return calendarCycleTwo;
    }

    public void setCalendarCycleTwo(int calendarCycleTwo) {
        this.calendarCycleTwo = calendarCycleTwo;
    }

    public int getCalendarCycleThree() {
        return calendarCycleThree;
    }

    public void setCalendarCycleThree(int calendarCycleThree) {
        this.calendarCycleThree = calendarCycleThree;
    }

    public int getSceneMode() {
        return sceneMode;
    }

    public void setSceneMode(int sceneMode) {
        this.sceneMode = sceneMode;
    }

    public int getSessionSelection() {
        return sessionSelection;
    }

    public void setSessionSelection(int sessionSelection) {
        this.sessionSelection = sessionSelection;
    }

    public int getSingleAlgorithm() {
        return singleAlgorithm;
    }

    public void setSingleAlgorithm(int singleAlgorithm) {
        this.singleAlgorithm = singleAlgorithm;
    }

    public int getQValue() {
        return qValue;
    }

    public void setQValue(int qValue) {
        this.qValue = qValue;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getFlip() {
        return flip;
    }

    public void setFlip(int flip) {
        this.flip = flip;
    }

    public int getRepeatUntilThereNoLabel() {
        return repeatUntilThereNoLabel;
    }

    public void setRepeatUntilThereNoLabel(int repeatUntilThereNoLabel) {
        this.repeatUntilThereNoLabel = repeatUntilThereNoLabel;
    }

    public int getMinimumQValue() {
        return minimumQValue;
    }

    public void setMinimumQValue(int minimumQValue) {
        this.minimumQValue = minimumQValue;
    }

    public int getMaximumQValue() {
        return maximumQValue;
    }

    public void setMaximumQValue(int maximumQValue) {
        this.maximumQValue = maximumQValue;
    }

    public int getInitialQValue() {
        return initialQValue;
    }

    public void setInitialQValue(int initialQValue) {
        this.initialQValue = initialQValue;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getFsa() {
        return fsa;
    }

    public void setFsa(int fsa) {
        this.fsa = fsa;
    }

    public int getAirProfile() {
        return airProfile;
    }

    public void setAirProfile(int airProfile) {
        this.airProfile = airProfile;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public int getElectricityLevel() {
        return electricityLevel;
    }

    public void setElectricityLevel(int electricityLevel) {
        this.electricityLevel = electricityLevel;
    }

    public int getBuzzerStatus() {
        return buzzerStatus;
    }

    public void setBuzzerStatus(int buzzerStatus) {
        this.buzzerStatus = buzzerStatus;
    }

    public int getLockNumber() {
        return lockNumber;
    }

    public void setLockNumber(int lockNumber) {
        this.lockNumber = lockNumber;
    }

    public int getLightLayerNumber() {
        return lightLayerNumber;
    }

    public void setLightLayerNumber(int lightLayerNumber) {
        this.lightLayerNumber = lightLayerNumber;
    }

    public ArrayList<Integer> getLightNumbers() {
        return lightNumbers;
    }

    public void setLightNumbers(ArrayList<Integer> lightNumbers) {
        this.lightNumbers = lightNumbers;
    }
}
