//package org.java.bi.ck.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import ru.yandex.clickhouse.ClickHouseConnection;
//import ru.yandex.clickhouse.ClickHouseDataSource;
//import ru.yandex.clickhouse.settings.ClickHouseProperties;
//
//import java.sql.*;
//import java.util.*;
//
//@Configuration
//@EnableConfigurationProperties(ClickHouseConfigProperties.class)
//public class ClickHouseConfig {
//
//
//    private final ClickHouseConfigProperties properties;
//
//    public ClickHouseConfig(ClickHouseConfigProperties properties) {
//        this.properties = properties;
//    }
//
//
//    @Bean
//    public Connection getClickHouseConn() {
//        ClickHouseConnection conn = null;
//        ClickHouseProperties properties = new ClickHouseProperties();
//
//        properties.setUser(this.properties.getUsername());
//        properties.setPassword(this.properties.getPassword());
//        properties.setDatabase(this.properties.getDb());
//        properties.setSocketTimeout(this.properties.getSocketTimeout());
//        ClickHouseDataSource clickHouseDataSource = new ClickHouseDataSource(this.properties.getAddress(),properties);
//        try {
//            conn = clickHouseDataSource.getConnection();
//            return conn;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//
//}
