package com.cse.mail;


import com.cse.mail.dal.model.Contact;
import com.cse.mail.dal.model.User;
import com.cse.mail.dal.repository.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;



@SpringBootApplication
public class MailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }
    private Faker faker = new Faker();
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){
        return args -> {
            User user = new User();
            user.setUsername(faker.name().username());

            List<Contact> contacts = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Contact contact = new Contact();
                contact.setName(faker.name().fullName());
                contact.setContactUser(user);
                contacts.add(contact);
            }
            user.setContacts(contacts);
            User createdUser = userRepository.save(user);
        };
    }
    public void customize(ConfigurableWebServerFactory factory) {
        // Set the desired port
        factory.setPort(8000);
    }

}



