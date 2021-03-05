package com.unicom.excelforunicom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@EnableScheduling
@EnableAsync
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@MapperScan(basePackages = {"com.unicom.excelforunicom.business.**.dao"})
public class ExcelforunicomApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelforunicomApplication.class, args);
    }

}
