package com.cse.mail.Builder.DataBaseBuilder;

import com.cse.mail.dal.model.Attachment;
import com.cse.mail.dal.model.Email;
import com.cse.mail.dal.model.User;
import java.util.*;
import java.time.LocalDateTime;

public class EmailBuilder {

    private User sender;
    private List<User> receivers;
    private String subject;
    private String body;
    private LocalDateTime timestamp;
    private boolean isDraft;
    private List<Attachment> attachments;
    private int priority;

    public EmailBuilder setSender(User sender) {
        this.sender = sender;
        return this;
    }
    public EmailBuilder setReceivers(List<User> receivers) {
        this.receivers = receivers;
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
    public EmailBuilder setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
    public EmailBuilder setIsDraft(boolean isDraft) {
        this.isDraft = isDraft;
        return this;
    }
    public EmailBuilder setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }
    public EmailBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }
    
    public Email build() {
        return new Email(sender, receivers, subject, body, timestamp, isDraft, attachments, priority);
    }

    

}
