package com.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 个人-首页模块
 */
@EnableAsync
@MapperScan("com.cloud.**.mapper")
@SpringBootApplication
public class CloudApiApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CloudApiApplication.class, args);
        System.out.println("后台模块启动成功");
    }
}
