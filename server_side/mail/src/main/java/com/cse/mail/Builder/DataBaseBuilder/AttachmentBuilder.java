package com.cse.mail.Builder.DataBaseBuilder;

import com.cse.mail.dal.model.Attachment;

public class AttachmentBuilder {

    private String path;
    private String filename;

    public AttachmentBuilder setPath(String path) {
        this.path = path;
        return this;
    }
    public AttachmentBuilder setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public Attachment build() {
        return new Attachment(path, filename);
    }
    
    
}
