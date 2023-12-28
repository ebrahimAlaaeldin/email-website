package com.cse.mail.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cse.mail.dal.model.Attachment} entity
 */
public class AttachmentDto implements Serializable {
    private int attachmentId;
    private String path;
    private String filename;

    public AttachmentDto(){

    }

    public AttachmentDto(int attachmentId, String path, String filename) {
        this.attachmentId = attachmentId;
        this.path = path;
        this.filename = filename;
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public String getPath() {
        return path;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentDto entity = (AttachmentDto) o;
        return Objects.equals(this.attachmentId, entity.attachmentId) &&
                Objects.equals(this.path, entity.path) &&
                Objects.equals(this.filename, entity.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attachmentId, path, filename);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + attachmentId + ", " +
                "path = " + path + ", " +
                "filename = " + filename + ")";
    }
}