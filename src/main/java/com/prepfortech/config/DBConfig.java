package com.prepfortech.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

public class DBConfig {

    @Value("jdbc:mysql://database-1.csj15u161rng.ap-south-1.rds.amazonaws.com")
    private String jdbcUrl;

    @Value("AVJETBRAINS")
    private String username;

    @Value("Madhu@1976")
    private String password;

    @Bean(destroyMethod = "close")
    @Primary
    DataSource getDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("com.mysql.jbc.Driver");
        return dataSource;
    }
}
