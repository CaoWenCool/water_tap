package com.blockchain.watertap;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableAsync
@MapperScan({"com.blockchain.watertap.mapper.*.mapper"})
public class WatertapApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatertapApplication.class, args);
    }

}
