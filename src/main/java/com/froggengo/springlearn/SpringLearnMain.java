package com.froggengo.springlearn;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.File;

@SpringBootApplication(exclude = {})//springboot不配置DataSource会报错 DataSourceAutoConfiguration.class、DruidDataSourceAutoConfigure
@MapperScan("com.froggengo.springlearn.project.mapper")
public class SpringLearnMain {
    public static void main(String[] args) {
        SpringApplication.run(SpringLearnMain.class,args);
       // new File("classpath:/application-hello.yml");
    }
}
