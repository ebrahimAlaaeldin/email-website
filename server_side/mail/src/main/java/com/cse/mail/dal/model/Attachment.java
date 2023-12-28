package com.cse.mail.dal.model;

import jakarta.persistence.*;

@Entity(name = "Attachment")
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "id",
            updatable = false
    )
    private int id;

    private String path;
    private String filename;

    public Attachment(){

    }

    public Attachment(String path, String filename) {
        this.path = path;
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}