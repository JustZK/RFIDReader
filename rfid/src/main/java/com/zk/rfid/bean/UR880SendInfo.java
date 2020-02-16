package com.zk.rfid.bean;

import com.zk.rfid.ur880.util.Utils.TYPE;

public class UR880SendInfo {
    private TYPE communicationType; //通信类型
    private String ID; //
    private int fastId; //0x01 启用FastID功能 0x00 不启用FastID功能
    private int inventoryType; //0x00 非连续 0x01 连续
    private int floor; //指示灯所在层
    private int region; //指示灯所在区域
    private int indicatorSwitch; //指示灯开关指令

    private int antennaPower; //天线功率 10-30  0.1dBm(下发范围是100-300）
    private int dwellTime; //驻留时间 毫秒
    private int calendarCycle; //盘讯周期 默认是0

    private int antennaNumber; //天线号 (1-4)

    private int session; //Session s0, s1, s2, s3
    private int singleAlgorithm; //单化算法 0x00 固定算法 0x01 动态算法
    private int qValue; //Q值 在配置为动态算法时，表示起始Q值
    private int retries; //重复次数  1次
    private int flip; //翻转 0x00 不翻转 0x01 翻转
    private int repeatUntilThereNoLabel; //重复直至无标签
    private int minimumQValue; //最小Q值 阀值在动态算法下有效 默认是0
    private int maximumQValue; //最大Q值 阀值在动态算法下有效 默认是15
    private int threshold; //阀值 默认是4

    public static class Builder {
        UR880SendInfo mUR880SendInfo;

        public Builder() {
            mUR880SendInfo = new UR880SendInfo();
        }

        // 2.5	Host请求读写器软硬件版本信息
        public Builder getVersionInformation(String ID) {
            mUR880SendInfo.setCommunicationType(TYPE.GET_VERSION_INFO_R);
            mUR880SendInfo.setID(ID);
            return this;
        }

//        // 盘点
//        public Builder inventory(int ID, int fastId, int inventoryType) {
//            mUR880SendInfo.setCommunicationType(TYPE.INVENTORY_H);
//            mUR880SendInfo.setID(ID);
//            mUR880SendInfo.setFastId(fastId);
//            mUR880SendInfo.setInventoryType(inventoryType);
//
//            return this;
//        }



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

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getIndicatorSwitch() {
        return indicatorSwitch;
    }

    public void setIndicatorSwitch(int indicatorSwitch) {
        this.indicatorSwitch = indicatorSwitch;
    }

    public int getAntennaPower() {
        return antennaPower;
    }

    public void setAntennaPower(int antennaPower) {
        this.antennaPower = antennaPower;
    }

    public int getDwellTime() {
        return dwellTime;
    }

    public void setDwellTime(int dwellTime) {
        this.dwellTime = dwellTime;
    }

    public int getCalendarCycle() {
        return calendarCycle;
    }

    public void setCalendarCycle(int calendarCycle) {
        this.calendarCycle = calendarCycle;
    }

    public int getAntennaNumber() {
        return antennaNumber;
    }

    public void setAntennaNumber(int antennaNumber) {
        this.antennaNumber = antennaNumber;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
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

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
