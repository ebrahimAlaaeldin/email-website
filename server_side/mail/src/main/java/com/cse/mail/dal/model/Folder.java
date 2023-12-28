package com.cse.mail.dal.model;

import jakarta.persistence.*;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

@Entity(name = "Folder")
@Table(name = "folders")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String folderName;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    private boolean isRemovable;

    public boolean isRemovable() {
        return isRemovable;
    }

    public void setRemovable(boolean isRemovable) {
        this.isRemovable = isRemovable;
    }

    private boolean isRenamable;

    public boolean isRenamable() {
        return isRenamable;
    }

    public void setRenamable(boolean isRenamable) {
        this.isRenamable = isRenamable;
    }
    

    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(
            name = "folder_emails",
            joinColumns = @JoinColumn(name = "folder_id"),
            inverseJoinColumns = @JoinColumn(name = "email_id")
    )
    private List<Email> emails;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Filter> filters;

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public Folder() {
    }

    public Folder(User user, String folderName, boolean isRemovable, boolean isRenamable, List<Email> emails, List<Filter> filters) {
        this.user = user;
        this.folderName = folderName;
        this.isRemovable = isRemovable;
        this.isRenamable = isRenamable;
        this.emails = emails;
        this.filters = filters;
    }
}