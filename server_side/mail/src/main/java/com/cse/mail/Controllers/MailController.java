package com.cse.mail.Controllers;

import com.cse.mail.controls.EmailSpecification;
//import com.cse.mail.controls.Director;
import com.cse.mail.controls.SearchCriteria;
import com.cse.mail.dal.model.Email;
import com.cse.mail.dal.repository.EmailRepository;
import com.cse.mail.dal.repository.FolderRepository;
import com.cse.mail.dto.*;

import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/api/{username}/email")
public class MailController {
//    Director director = Director.getInstance();
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private ModelMapper modelMapper;

    Faker faker = new Faker();

    @PostMapping(value = "/create")
    public int createEmail(@PathVariable String username,@RequestParam("file") List<MultipartFile> file, @ModelAttribute EmailDto emailDto) {
        int id = (int)faker.number().randomNumber();
//        System.out.println(emailDto);
//        System.out.println(files);
        // Here you would typically save the emailDto to the database
        return id;
    }

    @PostMapping("/draft")
    public int draftEmail(@PathVariable String username, @RequestBody EmailDto emailDto) {
        int id = (int)faker.number().randomNumber();
        System.out.println(emailDto);
        // Here you would typically save the emailDto to the database
        return id;
    }

    @PostMapping("/copy")
    public String copyEmail(@PathVariable String username, @RequestBody TransferDto transferDto) {
        System.out.println(transferDto);
        // Here you would typically copy the email in the database
        return "Email copied successfully";
    }


    @DeleteMapping("/delete")
    public String deleteEmail(@PathVariable String username, @RequestParam int emailId) {
        // Here you would typically delete the email from the database
        return "Email with ID " + emailId + " deleted successfully";
    }

    @PostMapping("/move")
    public String moveEmail(@PathVariable String username, @RequestBody TransferDto transferEmailDto) {
        System.out.println(transferEmailDto);
        int id = (int)faker.number().randomNumber();
        // Here you would typically move the email in the database
        return "Email moved successfully with new ID " + id;
    }

    @PostMapping("/list")
    public EmailsRequestDto listEmails(@PathVariable String username, @RequestBody SearchEmailDto searchEmailDto) {

            // Create a Specification based on the search criteria
            EmailSpecification spec = new EmailSpecification(new SearchCriteria(searchEmailDto.getSearchType(), ":", searchEmailDto.getSearchFor()));

            // Create a Pageable with the page number and number of elements per page
            Pageable pageable = PageRequest.of(searchEmailDto.getPageNumber(), searchEmailDto.getNumberOfElementsInPage(), Sort.by(searchEmailDto.getSortBy()));

            // Query the database and get a Page of Emails
            Page<Email> emails = emailRepository.findAll(spec, pageable);
            List<Email> emailList = emails.getContent();

            // Convert the Page of Emails to a Page of EmailDtos
            Page<EmailDto> emailDtos = emails.map(email -> modelMapper.map(email, EmailDto.class));

//            List<EmailDto> emailDtosList = new ArrayList<>();
//            for (Email email: emailList) {
//                EmailDto emailDto = new EmailDto(email.getId(),email.getSender());
//            }

            EmailsRequestDto emailsRequestDto = new EmailsRequestDto(emailDtos.getContent(),emails.getTotalPages());
            return emailsRequestDto;
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
