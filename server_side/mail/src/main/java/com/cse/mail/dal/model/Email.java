package com.cse.mail.dal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Email")
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private int id;

    @ManyToMany(mappedBy = "emails", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Folder> folders;

    @ManyToOne
    private User sender;

    @ManyToMany
    @JoinTable(
            name = "email_recipients",
            joinColumns = @JoinColumn(name = "email_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> receivers;


    private String subject;

    @Column(
            name = "body",
            length = 1000
    )
    private String body;

    public Email() {
    }

    public Email(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Email(User sender, List<User> receivers, String subject, String body, LocalDateTime timestamp, List<Folder> folders ,boolean isDraft, List<Attachment> attachments, int priority) {
        this.sender = sender;
        this.receivers = receivers;
        this.folders =folders;
        this.subject = subject;
        this.body = body;
        this.timestamp = timestamp;
        this.isDraft = isDraft;
        this.attachments = attachments;
        this.priority = priority;
    }

    private LocalDateTime timestamp;
    private boolean isDraft;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "attachment_email_id")
    private List<Attachment> attachments;

    private int priority;

    public int getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public List<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers = receivers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }
}