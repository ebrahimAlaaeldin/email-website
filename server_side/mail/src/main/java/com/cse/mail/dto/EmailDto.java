package com.cse.mail.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.cse.mail.dal.model.Email} entity
 */
public class EmailDto implements Serializable {
    private final int emailId;
    private final String sender;
    private final List<String> receivers;
    private final String subject;
    private final String body;
    private final LocalDateTime timestamp;
    private final boolean isDraft;
    private final List<AttachmentDto> attachments;
    private final int priority;

    public EmailDto(int emailId, String sender, List<String> receivers, String subject, String body, LocalDateTime timestamp, boolean isDraft, List<AttachmentDto> attachments, int priority) {
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

    public LocalDateTime getTimestamp() {
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
                Objects.equals(this.attachments, entity.attachments) &&
                Objects.equals(this.priority, entity.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailId, sender, receivers, subject, body, timestamp, isDraft, attachments, priority);
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