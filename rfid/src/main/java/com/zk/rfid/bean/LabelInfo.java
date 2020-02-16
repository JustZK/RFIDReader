package com.zk.rfid.bean;

public class LabelInfo {
    private int BoxID; // 天线所在柜体号
    private int floor; // 天线所在层
    private int antennaNumber; // 天线号
    private int areaNumber; // 区域号
    private int countingTimes; // 盘点次数
    private int RSSI; // RSSI
    private String EPC; // EPC
    private String TID; // TID

    public LabelInfo(){}

    public LabelInfo(int BoxID, int floor, int antennaNumber, int areaNumber, int countingTimes, int RSSI, String EPC, String TID){
        this.BoxID = BoxID;
        this.floor = floor;
        this.antennaNumber = antennaNumber;
        this.areaNumber = areaNumber;
        this.countingTimes = countingTimes;
        this.RSSI = RSSI;
        this.EPC = EPC;
        this.TID = TID;
    }

    public int getBoxID() {
        return BoxID;
    }

    public void setBoxID(int boxID) {
        BoxID = boxID;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getAntennaNumber() {
        return antennaNumber;
    }

    public void setAntennaNumber(int antennaNumber) {
        this.antennaNumber = antennaNumber;
    }

    public int getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(int areaNumber) {
        this.areaNumber = areaNumber;
    }

    public int getCountingTimes() {
        return countingTimes;
    }

    public void setCountingTimes(int countingTimes) {
        this.countingTimes = countingTimes;
    }

    public int getRSSI() {
        return RSSI;
    }

    public void setRSSI(int RSSI) {
        this.RSSI = RSSI;
    }

    public String getEPC() {
        return EPC;
    }

    public void setEPC(String EPC) {
        this.EPC = EPC;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }
}
