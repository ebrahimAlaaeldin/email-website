package com.cse.mail.Builder.DtoBuilder;

import java.util.List;

import com.cse.mail.dto.EmailDto;

public class EmailDtoBuilder {

    private int emailId;
    private String sender;
    private List<String> receivers;
    private String subject;
    private String body;
    private String timestamp;
    private boolean isDraft;
    private int priority;

    public EmailDtoBuilder setEmailId(int emailId) {
        this.emailId = emailId;
        return this;
    }

    public EmailDtoBuilder setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public EmailDtoBuilder setReceivers(List<String> receivers) {
        this.receivers = receivers;
        return this;
    }

    public EmailDtoBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public EmailDtoBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public EmailDtoBuilder setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public EmailDtoBuilder setIsDraft(boolean isDraft) {
        this.isDraft = isDraft;
        return this;
    }

    public EmailDtoBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public EmailDto build() {

        return new EmailDto(emailId, sender, receivers, subject, body, timestamp, isDraft, priority);
    }

}
