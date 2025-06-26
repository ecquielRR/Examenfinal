package com.EzequielRosales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
@ServletComponentScan 
public class LoginYRegistroApplication extends SpringBootServletInitializer { 

    public static void main(String[] args) {
        SpringApplication.run(LoginYRegistroApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LoginYRegistroApplication.class);
    }
}
