package com.cse.mail.dal.model;

import jakarta.persistence.*;
import java.util.List;

@Entity(name = "Folder")
@Table(name = "folders")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    private String folderName;
    private boolean isRemovable;
    private boolean isRenamable;

    @ManyToMany()
    @JoinTable(
            name = "folder_emails",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    private List<Email> emails;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Filter> filters;
}