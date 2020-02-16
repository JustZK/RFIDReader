package com.zk.rfid.callback;

public interface FactorySettingListener {
    /**
     * 天线配置
     * @param isSuccess 是否设置成功
     */
    void antennaConfigurationResult(int id, boolean isSuccess, int errorNumber);

    /**
     * 天线配置查询
     * @param isSuccess 是否获取成功
     * @param antennaPower 10-30  0.1dBm(下发范围是100-300）
     * @param dwellTime  驻留时间 毫秒
     * @param calendarCycle  盘讯周期 默认是0
     */
    void antennaConfigurationQuery(int id, boolean isSuccess, int antennaPower, int dwellTime, int calendarCycle);

    /**
     * 获取天线驻波比
     * @param isSuccess  是否获取成功
     * @param antennaNumber  天线号 1端口设备该值为1，四端口设备为1-4
     * @param antennaPower  驻波比 为实际驻波比乘100后的值
     */
    void obtainingAntennaStandingWaveRatioResult(int id, boolean isSuccess, int antennaNumber, int antennaPower);

    /**
     * 设置标签算法
     * @param isSuccess 是否设置成功
     */
    void labelAlgorithmSettingsResult(int id, boolean isSuccess, int errorNumber);


    /**
     * 标签算法读取
     * @param id                          设备ID
     * @param isSuccess                   是否获取成功
     * @param session                     Session
     * @param singleAlgorithm             单化算法
     * @param qValue                      Q值
     * @param retries                     重试次数
     * @param flip                        翻转
     * @param repeatUntilThereNoLabel     重复直至无标签
     * @param minimumQValue               最小Q值
     * @param maximumQValue               最大Q值
     * @param threshold                   阀值
     */
    void tagAlgorithmReading(int id, boolean isSuccess, int session, int singleAlgorithm, int qValue,
                             int retries, int flip, int repeatUntilThereNoLabel,
                             int minimumQValue, int maximumQValue, int threshold);

}
