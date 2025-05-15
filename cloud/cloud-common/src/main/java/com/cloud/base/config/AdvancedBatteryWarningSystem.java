package com.cloud.base.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 高级电池预警系统
 * <p>该类用于评估电池健康状态并生成预警结果和维护建议。</p>
 * <p>通过多种指标（如容量保持率、电压一致性、内阻一致性等）计算电池健康评分，并根据评分和趋势生成预警等级。</p>
 */
public class AdvancedBatteryWarningSystem {

    // 指标权重（可根据电池类型调整）
    private static final double[] WEIGHTS = {0.25, 0.2, 0.2, 0.15, 0.1, 0.07, 0.03};
    private static final int TREND_WINDOW = 5;

    /**
     * 电池健康数据类
     * <p>封装了电池的各项健康指标及历史评分数据。</p>
     */
    public static class BatteryHealthData {
        private final double capacityRetention; // 容量保持率
        private final double voltageSigma; // 电压一致性（标准差）
        private final double irSigma; // 内阻一致性（标准差）
        private final double tempSpread; // 温差
        private final double selfDischarge; // 自放电率
        private final double maxTemp; // 最高温度
        private final double minTemp; // 最低温度
        private final List<Double> historyScores; // 历史评分数据

        /**
         * 构造方法
         *
         * @param builder 构建器对象
         */
        public BatteryHealthData(Builder builder) {
            this.capacityRetention = builder.capacityRetention;
            this.voltageSigma = builder.voltageSigma;
            this.irSigma = builder.irSigma;
            this.tempSpread = builder.tempSpread;
            this.selfDischarge = builder.selfDischarge;
            this.maxTemp = builder.maxTemp;
            this.minTemp = builder.minTemp;
            this.historyScores = builder.historyScores.stream()
                    .limit(TREND_WINDOW)
                    .collect(Collectors.toList());
        }

        // 各项指标的getter方法
        public double getCapacityRetention() { return capacityRetention; }
        public double getVoltageSigma() { return voltageSigma; }
        public double getIrSigma() { return irSigma; }
        public double getTempSpread() { return tempSpread; }
        public double getSelfDischarge() { return selfDischarge; }
        public double getMaxTemp() { return maxTemp; }
        public double getMinTemp() { return minTemp; }
        public int getHistorySize() { return historyScores.size(); }
        public List<Double> getHistoryScores() { return historyScores; }

        /**
         * 构建器类
         * <p>用于构建BatteryHealthData对象，支持链式调用。</p>
         */
        public static class Builder {
            private double capacityRetention = 1.0;
            private double voltageSigma = 0;
            private double irSigma = 0;
            private double tempSpread = 0;
            private double selfDischarge = 0;
            private double maxTemp = 25;
            private double minTemp = 25;
            private List<Double> historyScores = new ArrayList<>();

            public Builder setCapacityRetention(double cr) {
                this.capacityRetention = cr;
                return this;
            }
            public Builder setVoltageSigma(double vs) {
                this.voltageSigma = vs;
                return this;
            }
            public Builder setIrSigma(double ir) {
                this.irSigma = ir;
                return this;
            }
            public Builder setTempSpread(double ts) {
                this.tempSpread = ts;
                return this;
            }
            public Builder setSelfDischarge(double sd) {
                this.selfDischarge = sd;
                return this;
            }
            public Builder setMaxTemp(double mt) {
                this.maxTemp = mt;
                return this;
            }
            public Builder setMinTemp(double mt) {
                this.minTemp = mt;
                return this;
            }
            public Builder addHistoryScore(double score) {
                this.historyScores = this.historyScores.stream()
                        .limit(TREND_WINDOW - 1)
                        .collect(Collectors.toList());
                this.historyScores.add(score);
                return this;
            }
            public Builder setHistoryScores(List<Double> hs) {
                this.historyScores = hs.stream()
                        .limit(TREND_WINDOW)
                        .collect(Collectors.toList());
                return this;
            }
            public BatteryHealthData build() {
                return new BatteryHealthData(this);
            }
        }
    }

    /**
     * 预警结果类
     * <p>封装了电池健康评分、趋势、预警等级及维护建议。</p>
     */
    public static class WarningResult {
        private WarningLevel level = WarningLevel.GREEN; // 预警等级
        private double currentScore; // 当前健康评分
        private double trend; // 健康趋势
        private final StringBuilder suggestions = new StringBuilder(); // 维护建议

        public WarningLevel getLevel() { return level; }
        public void setLevel(WarningLevel level) { this.level = level; }
        public double getCurrentScore() { return currentScore; }
        public void setCurrentScore(double currentScore) {
            this.currentScore = Math.max(0, Math.min(100, currentScore));
        }
        public double getTrend() { return trend; }
        public void setTrend(double trend) { this.trend = trend; }
        public void addSuggestion(String s) { suggestions.append("- ").append(s).append("\n"); }

        @Override
        public String toString() {
            return "【电池预警系统】\n" +
                    "预警等级: " + level + "\n" +
                    "当前健康评分: " + String.format("%.1f", currentScore) + "/100\n" +
                    "健康趋势: " + String.format("%.1f", trend) + "（最近" + TREND_WINDOW + "次变化）\n" +
                    "建议措施:\n" + suggestions.toString().trim();
        }
    }

    /**
     * 预警等级枚举
     * <p>定义了三种预警等级：绿色、黄色、红色。</p>
     */
    public enum WarningLevel { GREEN, YELLOW, RED }

    /**
     * 预测电池预警
     * <p>根据电池健康数据计算健康评分、趋势，并生成预警结果。</p>
     *
     * @param data 电池健康数据
     * @return 预警结果
     */
    public static WarningResult predictWarning(BatteryHealthData data) {
        WarningResult result = new WarningResult();

        // 1. 实时指标评分（0-100）
        double[] scores = {
                scoreCapacity(data),
                scoreVoltage(data),
                scoreInternalResistance(data.irSigma),
                scoreTemperature(data.tempSpread, data.maxTemp, data.minTemp),
                scoreSelfDischarge(data.selfDischarge),
                scoreMaxTemperature(data.maxTemp),
                scoreMinTemperature(data.minTemp)
        };

        // 2. 加权综合评分
        double currentScore = weightedScore(scores, WEIGHTS);
        result.setCurrentScore(currentScore);

        // 3. 趋势分析
        if (data.getHistorySize() >= TREND_WINDOW) {
            List<Double> recent = data.getHistoryScores().stream()
                    .skip(data.getHistorySize() - TREND_WINDOW)
                    .collect(Collectors.toList());
            result.setTrend(recent.get(TREND_WINDOW-1) - recent.get(0));
        }

        // 4. 决策逻辑（使用原始数据）
        determineWarningLevel(result, data);
        generateMaintenanceSuggestions(result, data);
        return result;
    }
    private static double scoreMaxTemperature(double maxT) {
        return maxT <= 45 ? 100 : maxT <= 55 ? 80 : maxT <= 65 ? 50 : 0;
    }

    private static double scoreMinTemperature(double minT) {
        return minT >= -10 ? 100 : minT >= -20 ? 80 : minT >= -30 ? 50 : 0;
    }

    private static double scoreCapacity(double cr) {
        // 容量保持率评分：>95%=100，80-95%线性，70-80%非线性，<70%=0
        return cr > 0.95 ? 100 :
                cr > 0.8  ? 80 + (cr - 0.8) * 100 :
                        cr > 0.7  ? 50 + (cr - 0.7) * 50 : 0;
    }

    private static double scoreVoltage(double sigma) {
        // 电压一致性（标准差mV）：<5mV=100，5-10mV=80，10-15mV=50，>15mV=0
        return sigma < 5  ? 100 :
                sigma < 10 ? 80 :
                        sigma < 15 ? 50 : 0;
    }

    private static double scoreInternalResistance(double sigma) {
        // 内阻一致性（标准差mΩ）：<10mΩ=100，10-20mΩ=80，20-30mΩ=50，>30mΩ=0
        return sigma < 10 ? 100 :
                sigma < 20 ? 80 :
                        sigma < 30 ? 50 : 0;
    }

    private static double scoreTemperature(double spread, double maxT, double minT) {
        // 温度评分=温差评分(60%) + 极值评分(40%)
        double spreadScore = spread < 3 ? 100 : spread < 5 ? 80 : spread < 8 ? 50 : 0;
        double extremeScore = (scoreMaxTemperature(maxT) + scoreMinTemperature(minT)) / 2;
        return spreadScore * 0.6 + extremeScore * 0.4;
    }

    private static double scoreSelfDischarge(double rate) {
        // 自放电率（%/天）：<0.03%=100，0.03-0.05%=80，0.05-0.08%=50，>0.08%=0
        return rate < 0.03 ? 100 :
                rate < 0.05 ? 80 :
                        rate < 0.08 ? 50 : 0;
    }

    // 所有评分方法改为接收BatteryHealthData
    private static double scoreCapacity(BatteryHealthData data) {
        return data.getCapacityRetention() > 0.95 ? 100 :
               data.getCapacityRetention() > 0.8  ? 80 + (data.getCapacityRetention() - 0.8) * 100 :
               data.getCapacityRetention() > 0.7  ? 50 + (data.getCapacityRetention() - 0.7) * 50 : 0;
    }

    private static double scoreVoltage(BatteryHealthData data) {
        return data.getVoltageSigma() < 5  ? 100 :
               data.getVoltageSigma() < 10 ? 80 :
               data.getVoltageSigma() < 15 ? 50 : 0;
    }
    private static double weightedScore(double[] scores, double[] weights) {
        if (scores.length != weights.length) {
            throw new IllegalArgumentException("评分项与权重数量不匹配");
        }
        double total = 0;
        for (int i = 0; i < scores.length; i++) {
            total += scores[i] * weights[i];
        }
        return Math.round(total * 10) / 10.0; // 保留一位小数
    }
    // 决策逻辑接收原始数据
    private static void determineWarningLevel(WarningResult res, BatteryHealthData data) {
        double score = res.getCurrentScore();
        double trend = res.getTrend();

        // 红色预警条件：当前评分<40 或 趋势下降>15分 且 最近3次异常
        boolean hasRecentAbnormal = data.getHistoryScores().stream()
            .skip(Math.max(0, data.getHistorySize() - 3))
            .anyMatch(s -> s < 70);

        if ((score < 40 || trend < -15) && hasRecentAbnormal) {
            res.setLevel(WarningLevel.RED);
        } else if (score < 70 || trend < -5) {
            res.setLevel(WarningLevel.YELLOW);
        } else {
            res.setLevel(WarningLevel.GREEN);
        }
    }

    // 建议生成使用原始数据
    private static void generateMaintenanceSuggestions(WarningResult res, BatteryHealthData data) {
        res.suggestions.setLength(0);
        switch (res.getLevel()) {
            case RED:
                res.addSuggestion("立即停止使用！容量保持率仅" +
                    String.format("%.1f%%", data.getCapacityRetention()*100) + "（正常>80%）");
                res.addSuggestion("温差" + data.getTempSpread() + "℃超标（建议<5℃），检查散热系统");
                res.addSuggestion("内阻标准差" + data.getIrSigma() + "mΩ，建议24小时内检测单芯");
                break;
            case YELLOW:
                res.addSuggestion("本周完成均衡充电（电压标准差" + data.getVoltageSigma() + "mV）");
                res.addSuggestion("自放电率" + String.format("%.2f%%/天", data.getSelfDischarge()) +
                    "（正常<0.05%），建议记录72小时静置数据");
                res.addSuggestion("最高温" + data.getMaxTemp() + "℃接近阈值（60℃），注意环境温控");
                break;
            case GREEN:
                res.addSuggestion("状态良好，建议下次巡检：" +
                    (data.getHistorySize() < 1 ? "立即" : "1个月后"));
                break;
        }
        // 增加数据来源说明
        res.addSuggestion("\n（数据来源：容量/电压/内阻/温差/" +
            "自放电/" + data.getMaxTemp() + "℃/" + data.getMinTemp() + "℃）");
    }

    // 示例运行（包含历史数据追加演示）
    public static void main(String[] args) {
        BatteryHealthData data = new BatteryHealthData.Builder()
            .setCapacityRetention(0.72)    // 72%容量
            .setVoltageSigma(14)           // 电压标准差14mV
            .setIrSigma(22)                // 内阻标准差22mΩ
            .setTempSpread(6)              // 温差6℃
            .setSelfDischarge(0.06)        // 自放电0.06%/天
            .setMaxTemp(56)                // 最高温56℃
            .addHistoryScore(88)           // 历史数据（自动保留最近5次）
            .addHistoryScore(82)
            .addHistoryScore(75)
            .addHistoryScore(68)
            .addHistoryScore(62)
            .build();

        WarningResult result = predictWarning(data);
        System.out.println(result);
    }
}