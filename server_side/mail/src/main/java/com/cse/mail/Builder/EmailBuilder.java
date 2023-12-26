package com.cse.mail.Builder;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;
public class EmailBuilder {

    private String to;
    private String from;
    private String subject;
    private String body;
    private List<MultipartFile> attachments;
    private Date date;

    Boolean isDraft = false;
    int priority = 0;

    public EmailBuilder() {
    }

    public EmailBuilder setTo(String to) {
        this.to = to;
        return this;
    }

    public EmailBuilder setFrom(String from) {
        this.from = from;
        return this;
    }

    public EmailBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public EmailBuilder setFiles(List<MultipartFile> attachments) {
        this.attachments = attachments;
        return this;
    }
    public EmailBuilder setDate(Date date){
        this.date = date;
        return this;
    }
    public EmailBuilder setIsDraft(Boolean isDraft){
        this.isDraft = isDraft;
        return this;
    }
    public EmailBuilder setPriority(int priority){
        this.priority = priority;
        return this;
    }
    public Email createEmail() {
        return new Email(to, from, subject, body, files);
    }
}
