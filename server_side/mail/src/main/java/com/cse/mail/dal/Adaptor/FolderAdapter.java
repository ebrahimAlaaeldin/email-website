package com.cse.mail.dal.Adaptor;

import com.cse.mail.dto.FolderDto;
import com.cse.mail.dto.UserDto;
import com.cse.mail.dal.model.User;
import com.cse.mail.dal.repository.FolderRepository;
import com.cse.mail.dal.repository.UserRepository;
import com.cse.mail.dal.model.Contact; // Import other necessary classes
import com.cse.mail.dal.model.Folder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

public class FolderAdapter {

    @Autowired
    private FolderRepository folderRepository;

    public Folder dtoToEntity(FolderDto dto) {
        Folder folder;
        if (dto.getFolderId() == 0) {
            folder = new Folder();
            folder.setFolderName(dto.getFolderName());
            folder.setRemovable(dto.getIsRemovable());
            folder.setRenamable(dto.getIsRenamable());
            return folder;
        } else {
            folder = folderRepository.findById(dto.getFolderId()).orElse(null);
            if (folder == null) {
                throw new IllegalArgumentException("Folder with id " + dto.getFolderId() + " not found");
            }
            return folder;
        }
    }

    public  FolderDto entityToDto(Folder folder) {
        
        FolderDto dto = new FolderDto(folder.getId(), folder.getFolderName(), folder.isRemovable(), folder.isRenamable());
        return dto;
    }

}