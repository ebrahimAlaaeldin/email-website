package com.cse.mail.dal.repository;

import com.cse.mail.dal.model.Contact;
import com.cse.mail.dal.model.User;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private Faker faker;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setUsername(faker.name().username());

        List<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Contact contact = new Contact();
            contact.setName(faker.name().fullName());
            contacts.add(contact);
        }
        user.setContacts(contacts);

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        User foundUser = userRepository.findById(1).orElse(null);
        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
        assertEquals(user.getContacts().size(), foundUser.getContacts().size());
        System.out.println(foundUser.toString());
    }
}