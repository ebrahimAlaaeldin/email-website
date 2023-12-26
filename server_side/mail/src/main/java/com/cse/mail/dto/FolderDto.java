package com.cse.mail.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cse.mail.dal.model.Folder} entity
 */
public class FolderDto implements Serializable {
    private final int folderId;
    private final String folderName;
    private final boolean isRemovable;
    private final boolean isRenameable;

    public FolderDto(int folderId, String folderName, boolean isRemovable, boolean isRenameable) {
        this.folderId = folderId;
        this.folderName = folderName;
        this.isRemovable = isRemovable;
        this.isRenameable = isRenameable;
    }

    public int getFolderId() {
        return folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public boolean getIsRemovable() {
        return isRemovable;
    }

    public boolean getIsRenamable() {
        return isRenameable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FolderDto entity = (FolderDto) o;
        return Objects.equals(this.folderId, entity.folderId) &&
                Objects.equals(this.folderName, entity.folderName) &&
                Objects.equals(this.isRemovable, entity.isRemovable) &&
                Objects.equals(this.isRenameable, entity.isRenameable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(folderId, folderName, isRemovable, isRenameable);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + folderId + ", " +
                "folderName = " + folderName + ", " +
                "isRemovable = " + isRemovable + ", " +
                "isRenamable = " + isRenameable + ")";
    }
}