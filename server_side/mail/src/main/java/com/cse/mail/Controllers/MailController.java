package com.cse.mail.Controllers;

//import com.cse.mail.controls.Director;
import com.cse.mail.dal.model.Email;
import com.cse.mail.dal.repository.EmailRepository;
import com.cse.mail.dto.*;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class MailController {
//    Director director = Director.getInstance();
    @Autowired
    private EmailRepository emailrepository ;

    Faker faker = new Faker();

    @PostMapping(value = "/{username}/email/create")
    public int createEmail(@PathVariable String username,@RequestParam("file") MultipartFile file, @ModelAttribute EmailDto emailDto) {
        int id = (int)faker.number().randomNumber();
//        System.out.println(emailDto);
//        System.out.println(files);
        // Here you would typically save the emailDto to the database
        return id;
    }

    @PostMapping("/{username}/email/draft")
    public int draftEmail(@PathVariable String username, @RequestBody EmailDto emailDto) {
        int id = (int)faker.number().randomNumber();
        System.out.println(emailDto);
        // Here you would typically save the emailDto to the database
        return id;
    }

    @PostMapping("/{username}/email/copy")
    public String copyEmail(@PathVariable String username, @RequestBody TransferDto transferDto) {
        System.out.println(transferDto);
        // Here you would typically copy the email in the database
        return "Email copied successfully";
    }


    @DeleteMapping("/{username}/email/delete/{emailId}")
    public String deleteEmail(@PathVariable String username, @PathVariable int emailId) {
        // Here you would typically delete the email from the database
        return "Email with ID " + emailId + " deleted successfully";
    }

    @PostMapping("/{username}/email/move")
    public String moveEmail(@PathVariable String username, @RequestBody TransferDto transferEmailDto) {
        System.out.println(transferEmailDto);
        int id = (int)faker.number().randomNumber();
        // Here you would typically move the email in the database
        return "Email moved successfully with new ID " + id;
    }

    @PostMapping("/{username}/email/list")
    public EmailsRequestDto listEmails(@PathVariable String username, @RequestBody SearchEmailDto searchEmailDto) {
        System.out.println(searchEmailDto);
        List<EmailDto> emails = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> receivers = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                receivers.add(faker.internet().emailAddress());
            }
            List<AttachmentDto> attachments = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                attachments.add(new AttachmentDto(j, faker.file().fileName(),  faker.file().fileName()));
            }
            emails.add(new EmailDto(i, faker.internet().emailAddress(), receivers, faker.lorem().sentence(), faker.lorem().paragraph(), "dsad", faker.bool().bool(), faker.number().numberBetween(1, 4)));
        }
        return new EmailsRequestDto(emails,3);
    }

//    @PostMapping("/multiple")
//    public String uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) {
//        // Handle the uploaded files
//        if (!files.isEmpty()) {
//            try {
//                for (MultipartFile file : files) {
//                    if (!file.isEmpty()) {
//                        byte[] bytes = file.getBytes();
//                        // Save or process each file as needed
//                    }
//                }
//                return "Files uploaded successfully!";
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "File upload failed: " + e.getMessage();
//            }
//        } else {
//            return "No files uploaded.";
//        }
//    }


}
