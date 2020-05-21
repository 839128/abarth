package org.aoju.bus.example;

import org.aoju.bus.starter.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Kimi Liu
 * @version 3.6.6
 * @since JDK 1.8+
 */
@EnableMapper
@EnableSwagger
//@EnableOffice
@EnableStorage
@EnableValidate
@EnableSensitive
@EnableOAuth2
@EnableWrapper
@SpringBootApplication
public class SpringXtestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringXtestApplication.class, args);
    }

}
