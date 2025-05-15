package com.cloud.base.entity;

public class BatteryParams {
    private double capacityRetentionRate;  // 容量保持率(%) ‌:ml-citation{ref="5,6" data="citationList"}
    private double voltageConsistency;     // 电压一致性(标准差 mV) ‌:ml-citation{ref="3,6" data="citationList"}
    private double resistanceConsistency;  // 内阻一致性(变异系数 %) ‌:ml-citation{ref="2,7" data="citationList"}
    private double tempConsistency;        // 温度一致性(极差 ℃) ‌:ml-citation{ref="8" data="citationList"}
    private double selfDischargeRate;      // 自放电率(%/月) ‌:ml-citation{ref="6,8" data="citationList"}
    private double maxTemp;                // 最高温度(℃) ‌:ml-citation{ref="8" data="citationList"}
    private double minTemp;                // 最低温度(℃) ‌:ml-citation{ref="8" data="citationList"}
}