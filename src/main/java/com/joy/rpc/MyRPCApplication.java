package com.joy.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Created by SongLiang on 2019-10-18
 */
@SpringBootApplication
@EnableConfigurationProperties
public class MyRPCApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyRPCApplication.class, args);
    }

}
