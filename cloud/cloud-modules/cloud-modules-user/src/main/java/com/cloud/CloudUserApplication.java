package com.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 用户模块
 */
@EnableAsync
@MapperScan("com.cloud.**.mapper")
@SpringBootApplication
public class CloudUserApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CloudUserApplication.class, args);
        System.out.println("用户服务模块启动成功");
           
    }
}
