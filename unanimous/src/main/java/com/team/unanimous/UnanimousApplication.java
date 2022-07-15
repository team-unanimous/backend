package com.team.unanimous;

import com.team.unanimous.model.chat.ChatRoom;
import com.team.unanimous.repository.chat.ChatRoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UnanimousApplication {

//    public static void main(String[] args) {
//
//        SpringApplication.run(UnanimousApplication.class, args);
//
//
//    }
public static final String APPLICATION_LOCATIONS = "spring.config.location="
        + "classpath:application.properties";

    public static void main(String[] args) {
        new SpringApplicationBuilder(UnanimousApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

//    @Bean
//    public CommandLineRunner demo(ChatRoomRepository repository){
//        return (args) -> {
//            repository.save(new ChatRoom("전체 채팅방"));
//        };
//    }

}
