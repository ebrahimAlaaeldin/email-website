package com.cse.mail.Builder.DtoBuilder;

import com.cse.mail.dto.FolderDto;

public class FolderDtoBuilder {
    private int folderId;
    private String folderName;
    private boolean isRemovable;
    private boolean isRenameable;

    public FolderDtoBuilder setFolderId(int folderId) {
        this.folderId = folderId;
        return this;
    }

    public FolderDtoBuilder setFolderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    public FolderDtoBuilder setRemovable(boolean isRemovable) {
        this.isRemovable = isRemovable;
        return this;
    }

    public FolderDtoBuilder setRenameable(boolean isRenameable) {
        this.isRenameable = isRenameable;
        return this;
    }

    public FolderDto build() {

        return new FolderDto(folderId, folderName, isRemovable, isRenameable);
    }

}
