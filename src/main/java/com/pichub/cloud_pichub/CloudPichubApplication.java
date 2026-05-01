package com.pichub.cloud_pichub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.pichub.cloud_pichub.mapper")
public class CloudPichubApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudPichubApplication.class, args);
    }

}
