package com.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 行业模块
 */
@EnableAsync
@MapperScan("com.cloud.**.mapper")
@SpringBootApplication
public class CloudLogisticsApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CloudLogisticsApplication.class, args);
        System.out.println("通知模块启动成功");
    }
}
