package com.cse.mail.Controllers;

//import com.cse.mail.controls.Director;
import com.cse.mail.dal.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
@RequestMapping("/api")
public class FolderController {
//    Director director = Director.getInstance();
    @Autowired
    private FolderRepository folderrepository ;


    @PostMapping("/create folder")
    public String createfolder(/*some attributes*/) {

        return "folder created successfully";
    }
    @PostMapping("/delete folder")
    public String deletefolder(/*some attributes*/) {
        return "folder deleted successfully";
    }
    @PostMapping("load folders")
    public String loadFolders(/*some attributes*/) {
        return "folders loaded successfully";
    }
    @PostMapping("rename folder")
    public String renameFolder(/*some attributes*/) {
        return "folder renamed successfully";
    }
}
