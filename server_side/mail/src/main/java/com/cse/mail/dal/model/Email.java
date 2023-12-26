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
    private String body;

    public Email() {
    }

    public Email(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Email(User sender, List<User> receivers, String subject, String body, LocalDateTime timestamp, boolean isDraft, List<Attachment> attachments, int priority) {
        this.sender = sender;
        this.receivers = receivers;
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
}