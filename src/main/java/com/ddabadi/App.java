package com.ddabadi;

/**
 * Created by deddy on 4/23/16.
 */

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class App extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        //System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(App.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(App.class);
        //super.configure(builder); //To change body of generated methods, choose Tools | Templates.
    }

}
