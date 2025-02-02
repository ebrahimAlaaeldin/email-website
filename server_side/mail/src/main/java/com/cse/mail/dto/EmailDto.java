package com.cse.mail.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.cse.mail.dal.model.Email} entity
 */
public class EmailDto implements Serializable {
    private int emailId;
    private  String sender;
    private  List<String> receivers;
    private  String subject;
    private  String body;
    private  String timestamp;
    private  boolean isDraft;
    private List<AttachmentDto> attachments;
    private  int priority;

    public EmailDto() {}

    public EmailDto(int emailId, String sender, List<String> receivers, String subject, String body, String timestamp, boolean isDraft, List<AttachmentDto> attachments,int priority) {
        this.emailId = emailId;
        this.sender = sender;
        this.receivers = receivers;
        this.subject = subject;
        this.body = body;
        this.timestamp = timestamp;
        this.isDraft = isDraft;
        this.attachments = attachments;
        this.priority = priority;
    }

    public int getEmailId() {
        return emailId;
    }

    public String getSender() {
        return sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public boolean getIsDraft() {
        return isDraft;
    }

    public List<AttachmentDto> getAttachments() {
        return attachments;
    }

    public int getPriority() {
        return priority;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public void setAttachments(List<AttachmentDto> attachments) {
        this.attachments = attachments;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailDto entity = (EmailDto) o;
        return Objects.equals(this.emailId, entity.emailId) &&
                Objects.equals(this.sender, entity.sender) &&
                Objects.equals(this.receivers, entity.receivers) &&
                Objects.equals(this.subject, entity.subject) &&
                Objects.equals(this.body, entity.body) &&
                Objects.equals(this.timestamp, entity.timestamp) &&
                Objects.equals(this.isDraft, entity.isDraft) &&
//                Objects.equals(this.attachments, entity.attachments) &&
                Objects.equals(this.priority, entity.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailId, sender, receivers, subject, body, timestamp, isDraft, priority);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + emailId + ", " +
                "sender = " + sender + ", " +
                "receivers = " + receivers + ", " +
                "subject = " + subject + ", " +
                "body = " + body + ", " +
                "timestamp = " + timestamp + ", " +
                "isDraft = " + isDraft + ", " +
                "attachments = " + attachments + ", " +
                "priority = " + priority + ")";
    }
}