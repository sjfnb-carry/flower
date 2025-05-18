package com.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = {
        "com.mall.sys.mapper",
        "com.mall.goods.mapper"})
@EnableTransactionManagement
public class FlowerMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowerMallApplication.class, args);
    }

}
