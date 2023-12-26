package com.cse.mail.dal.repository;

import com.cse.mail.dal.model.Contact;
import com.cse.mail.dal.model.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private Faker faker;

    @BeforeEach
    public void setup() {
        faker = new Faker();
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername(faker.name().username());

        List<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Contact contact = new Contact();
            contact.setName(faker.name().fullName());
            contacts.add(contact);
        }
        user.setContacts(contacts);
        User createdUser = userRepository.save(user);
        User foundUser = userRepository.findById(createdUser.getId()).orElse(null);

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getContacts(), foundUser.getContacts());

        System.out.println(foundUser.toString());
    }
}