package org.java.bi.all;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.web.util.UrlPathHelper;


@SpringBootApplication(scanBasePackages = {"org.java.bi"},
        exclude = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
        })
@MapperScan("org.java.bi.db.dao")
@EnableTransactionManagement
@EnableScheduling

public class AllApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AllApplication.class, args);
    }



}
