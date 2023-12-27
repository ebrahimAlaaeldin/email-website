package com.cse.mail.Controllers;

//import com.cse.mail.controls.Director;
import com.cse.mail.dal.repository.FolderRepository;
import com.cse.mail.dto.FolderDto;

import java.util.Arrays;
import java.util.List;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/{username}/folder")
public class FolderController {
//    Director director = Director.getInstance();
    @Autowired
    private FolderRepository folderrepository ;
    private final Faker faker = new Faker();

    
    @PostMapping("/create")
    public int createFolder(@PathVariable String username, @RequestParam String folderName) {
        // Here you would typically create a folder and return its ID
        System.out.println(folderName);
        return faker.number().randomDigit();
    }

    @PostMapping("/delete")
    public String deleteFolder(@PathVariable String username, @RequestParam int folderId) {
        // Here you would typically delete the folder with the given ID
        System.out.println(folderId);
        return "Deleted folder with id "+folderId;
    }

    @PostMapping("/rename")
    public String renameFolder(@PathVariable String username, @RequestParam int folderId,@RequestParam int newFolderName) {
        // Here you would typically rename the folder with the given ID
        return "Renamed folder with id "+folderId + "to "+ newFolderName;
    }

    @GetMapping("/list")
    public List<FolderDto> listFolder(@PathVariable String username) {
        // Here you would typically fetch the folders
        
        return Arrays.asList(
            new FolderDto(
                1,
                "Inbox",
                faker.bool().bool(),
                faker.bool().bool()
            ),
            new FolderDto(
                2,
                "Draft",
                faker.bool().bool(),
                faker.bool().bool()
            ),
            new FolderDto(
                3,
                "Sent",
                faker.bool().bool(),
                faker.bool().bool()
            ),
            new FolderDto(
                4,
                "Trash",
                faker.bool().bool(),
                faker.bool().bool()
            ),
            new FolderDto(
                5,
                faker.lorem().word(),
                faker.bool().bool(),
                faker.bool().bool()
            ),
            new FolderDto(
                6,
                faker.lorem().word(),
                true,
                true
            )
        );
    }
}
