package com.cse.mail.Controllers;

import com.cse.mail.controls.Director;
import com.cse.mail.dal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
@RequestMapping("/api")
public class UserController {
    Director director = Director.getInstance();
    @Autowired
    private UserRepository userrepository ;

    @PostMapping("/login")
    public String login(@Validated @RequestBody FrontendUser user) {

        return "Logged in successfully";
    }
    @PostMapping("rename contact")
    public String renameContact(/*some attributes*/) {
        return "contact renamed successfully";
    }
    @PostMapping("load contacts")
    public String loadContacts(/*some attributes*/) {
        return "contacts loaded successfully";
    }
    @PostMapping("/create contacts")
    public String createcontacts(/*some attributes*/) {
        return "contacts created successfully";
    }
    @PostMapping("/delete contacts")
    public String deletecontacts(/*some attributes*/) {
        return "contacts deleted successfully";
    }
}
