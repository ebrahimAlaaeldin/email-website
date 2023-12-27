package com.cse.mail.Builder.DtoBuilder;

import com.cse.mail.dto.AttachmentDto;

public class AttachmentDtoBuilder {

    private int attachmentId;
    private String path;
    private String filename;

    public AttachmentDtoBuilder setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
        return this;
    }

    public AttachmentDtoBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public AttachmentDtoBuilder setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public AttachmentDto build() {
        return new AttachmentDto(attachmentId, path, filename);
    }

}
