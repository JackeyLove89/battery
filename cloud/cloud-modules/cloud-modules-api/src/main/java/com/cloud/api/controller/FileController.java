package com.cloud.api.controller;


import com.cloud.api.config.MinioConfig;
import com.cloud.base.api.CommonResult;
import com.cloud.base.config.Contance;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;


@RestController
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient client;

    // 上传头像
    @RequestMapping(value = "/upload")
    public CommonResult<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return CommonResult.failed("文件为空");
            }

            // 获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);

            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);

            // 生成唯一文件名
            String uniqueFileName = new Date().getTime() + suffixName;

            // 使用 MinIO 上传文件
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(uniqueFileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            client.putObject(args);

            // 返回文件的访问 URL
            String fileUrl = minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + uniqueFileName;
            return CommonResult.success(fileUrl);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.failed("上传失败");
    }
}

