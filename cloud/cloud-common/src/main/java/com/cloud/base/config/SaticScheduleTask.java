package com.cloud.base.config;


import com.cloud.base.entity.Dc;
import com.cloud.base.entity.Msg;
import com.cloud.base.mapper.DcMapper;
import com.cloud.base.mapper.MsgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {


     @Autowired
     DcMapper dcMapper;

    @Autowired
    MsgMapper msgMapper;
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate=10*60*1000)
    private void configureTasks() {

        List<Dc> parks= dcMapper.selectAll();
        for (int i = 0; i < parks.size(); i++) {
            Dc dc = parks.get(i);
            if (dc.getJkd()>=0){
                dc.setJkd(dc.getJkd()-(int) (Math.random() * 7) + 1);
            }
            if (dc.getDy()>=0){
                dc.setDy(dc.getDy()-(int) (Math.random() * 5) + 1);
            }
            if (dc.getNz()>=0){
                dc.setNz(dc.getNz()-(int) (Math.random() * 3) + 1);
            }
            if (dc.getZfd()>=0){
                dc.setZfd(dc.getZfd()-(int) (Math.random() * 7) + 1);
            }
            if (dc.getWd()>=0){
                dc.setWd(dc.getWd()-(int) (Math.random() * 5) + 1);
            }
            if (dc.getRl()>=0){
                dc.setRl(dc.getRl()-(int) (Math.random() * 4) + 1);
            }
            if (dc.getXh()>=0){
                dc.setXh(dc.getXh()-(int) (Math.random() * 10) + 1);
            }


            dc.setXslc(String.valueOf(Integer.parseInt(dc.getXslc())+(int) (Math.random() * 10) + 1));
            int n1 = (int) (Math.random() * 50) + 20;

            dc.setZgwd(String.valueOf(n1));
            dc.setZdwd(String.valueOf(n1-((int) (Math.random() * 10) + 1)));
            dc.setCtime(new Date());
            dcMapper.updateByPrimaryKey(dc);





                double dy =(double) dc.getDy() * 100 / 10000;

                double nz =(double) dc.getNz() * 100 / 10000;

                double rl =(double) dc.getRl() * 100 / 10000;

                double wd =(double) dc.getWd() * 100 / 10000;

                double zfd =(double) dc.getZfd() * 100 / 10000;

                int zgw = Integer.parseInt(dc.getZgwd());


                int zdw = Integer.parseInt(dc.getZdwd());

            AdvancedBatteryWarningSystem.BatteryHealthData data = new AdvancedBatteryWarningSystem.BatteryHealthData.Builder()
                    .setCapacityRetention(rl)    // 72%容量
                    .setVoltageSigma(dy)           // 电压标准差14mV
                    .setIrSigma(nz)                // 内阻标准差22mΩ
                    .setTempSpread(zfd-zdw)              // 温差6℃
                    .setSelfDischarge(zfd)        // 自放电0.06%/天
                    .setMaxTemp(zgw)                // 最高温56℃
                    .addHistoryScore((int) (Math.random() * 50) + 50)           // 历史数据（自动保留最近5次）
                    .addHistoryScore((int) (Math.random() * 50) + 50)
                    .addHistoryScore((int) (Math.random() * 50) + 50)
                    .addHistoryScore((int) (Math.random() * 50) + 50)
                    .addHistoryScore((int) (Math.random() * 50) + 50)
                    .build();

              AdvancedBatteryWarningSystem.WarningResult result =AdvancedBatteryWarningSystem.predictWarning(data);
            Msg msg = new Msg();
            msg.setUid(dc.getUid());
            msg.setCtime(new Date());
            msg.setCid(dc.getCid());
            msg.setDid(dc.getId());
            msg.setWd("未读");
            msg.setWarning(result.toString());
            msgMapper.insert(msg);

        }
    }


}