package com.team.unanimous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UnanimousApplication {

    public static void main(String[] args) {

        SpringApplication.run(UnanimousApplication.class, args);


    }

}
