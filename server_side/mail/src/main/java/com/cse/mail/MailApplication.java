package com.cse.mail;

import com.cse.mail.dal.model.User;
import com.cse.mail.dal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){
        return args -> {
            User ahmed = new User(
                    "Ahmed",
                    "Ayman",
                    "ahmedayman4a",
                    "secureP@ss"
            );
            userRepository.save(ahmed);
        };
    }
}
