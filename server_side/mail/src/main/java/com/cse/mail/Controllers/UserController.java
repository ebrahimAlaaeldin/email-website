package com.cse.mail.Controllers;

//import com.cse.mail.controls.Director;
import com.cse.mail.dal.repository.UserRepository;
import com.cse.mail.dto.ContactDto;
import com.cse.mail.dto.UserDto;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/api")
public class UserController {
//    Director director = Director.getInstance();
    @Autowired
    private UserRepository userrepository ;

    Faker faker = new Faker();

    @PostMapping("login")
    public UserDto login(@RequestParam String username) {
        UserDto user = new UserDto(1,username);
        return user;
    }



    @GetMapping("/{username}/contacts/list")
    public List<ContactDto> listContacts(@PathVariable String username) {
        List<ContactDto> contacts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<String> emails = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                emails.add(faker.internet().emailAddress());
            }
            contacts.add(new ContactDto(i, faker.name().fullName(), emails,5));
        }
        return contacts;
    }
    
    @DeleteMapping("/{username}/contacts/delete")
    public String deleteContact(@PathVariable String username, @RequestParam int contactId) {
        // Here you would typically delete the contact from the database
        return "Contact with ID " + contactId + " deleted successfully";
    }
    
    @PostMapping("/{username}/contacts/rename")
    public String renameContact(@PathVariable String username, @RequestParam int contactId, @RequestParam String newName) {
        // Here you would typically rename the contact in the database
        return "Contact with ID " + contactId + " renamed successfully to " + newName;
    }

    @PostMapping("/{username}/contacts/create")
    public String createContact(@PathVariable String username, @RequestBody ContactDto contactDto) {
    // Here you would typically save the contactDto to the database
    int id = faker.number().randomDigit();
    return "Contact created successfully with ID " + id;
}

}
