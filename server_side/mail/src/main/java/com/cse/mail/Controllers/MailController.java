package com.cse.mail.Controllers;

import com.cse.mail.controls.Director;
import com.cse.mail.dal.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/api")
public class MailController {
    Director director = Director.getInstance();
    @Autowired
    private EmailRepository emailrepository ;

//    @PostMapping("/create Mail")
//    public String sendMail(/*the email and it's specifications*/) {
//
//        return "Mail sent successfully";
//    }
    @PostMapping("/send mail")
    public String sendMail() {
        return "Mail sent successfully";
    }
    @PostMapping("/copy")
    public String CopyMail(/*the email and the folder to copy to */) {
        return "Mail Copied successfully";
    }
    @PostMapping("/delete mail")
    public String DeleteMail(/*some attributes*/) {
        return "Mail Deleted successfully";
    }

    @PostMapping("/move mail")
    public String moveMail(/*some attributes*/) {
        return "Mail moved successfully";
    }

    @PostMapping("/filter")
    public String filter(/*some attributes*/) {
        return "filter applied successfully";
    }
    @PostMapping("/sort")
    public String sort(/*some attributes*/) {
        return "sort applied successfully";
    }

    @PostMapping("load mails")
    public String loadMails(/*some attributes*/) {
        return "mails loaded successfully";
    }


    @PostMapping("remove mail")
    public String removeMail(/*some attributes*/) {
        return "mail removed successfully";
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
