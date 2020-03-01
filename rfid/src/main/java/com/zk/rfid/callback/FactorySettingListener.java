package com.zk.rfid.callback;

public interface FactorySettingListener {
    /**
     * 2.16	天线配置命令结果
     */
    void setAntennaConfigurationResult(boolean result, int errorNumber);

    /**
     * 2.18	天线配置查询应答
     */
    void getAntennaConfigurationResult(boolean result, int errorNumber,
                                       int antennaEnableZero, int antennaEnableOne, int antennaEnableTwo, int antennaEnableThree,
                                       int antennaPowerZero, int antennaPowerOne, int antennaPowerTwo, int antennaPowerThree,
                                       int dwellTimeZero, int dwellTimeOne, int dwellTimeTwo, int dwellTimeThree,
                                       int calendarCycleZero, int calendarCycleOne, int calendarCycleTwo, int calendarCycleThree);

    /**
     * 2.28	获取天线驻波比应答
     */
    void getAntennaStandingWaveRatioResult(float antennaZero, float antennaOne, float antennaTwo, float antennaThree);


    /**
     * 2.30	设置GPO引脚状态回应
     */
    void setGPOOutputStatusResult(boolean result, int errorNumber);

    /**
     * 2.32	读取GPI输入命令应答
     */
    void getGPIOutputStatusResult(boolean result, int errorNumber,
                                  int portZeroStatus, int portOneStatus, int portTwoStatus, int portThreeStatus);

    /**
     * 2.38	蜂鸣器状态配置应答
     */
    void setBuzzerStatusResult(int errorNumber);

    /**
     * 2.40	蜂鸣器状态获取命令应答
     */
    void getBuzzerStatusResult(int buzzerStatus);

    /**
     * 2.42	时间同步命令应答
     */
    void timeSynchronizationResult(int errorNumber);

    /**
     * 2.44	设备重启命令应答
     */
    void deviceRestartResult(int errorNumber);
}
