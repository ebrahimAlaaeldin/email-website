package com.cse.mail.Adaptor;

import com.cse.mail.dto.FolderDto;
import com.cse.mail.dal.repository.FolderRepository;
import com.cse.mail.Builder.DataBaseBuilder.FolderBuilder;
import com.cse.mail.dal.model.Folder;

import org.springframework.beans.factory.annotation.Autowired;

public class FolderAdapter {

    @Autowired
    private FolderRepository folderRepository;

    public Folder dtoToEntity(FolderDto dto) {
        Folder folder;
        folder = new FolderBuilder().setFolderName(dto.getFolderName()).setFolderName(dto.getFolderName()).setIsRemovable(dto.getIsRemovable()).setIsRenamable(dto.getIsRenamable()).build();
        return folder;
    }

    public  FolderDto entityToDto(Folder folder) {
        
        FolderDto dto = new FolderDto(folder.getId(), folder.getFolderName(), folder.isRemovable(), folder.isRenamable());
        return dto;
    }

}