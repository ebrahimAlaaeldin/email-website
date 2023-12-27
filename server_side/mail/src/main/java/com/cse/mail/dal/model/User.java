package com.cse.mail.dal.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity(name = "User")
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize =  1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private int id;
    @Column(
            name = "username",
            nullable = false,
            columnDefinition = "TEXT",
            unique = true
    )
    private String username;

    @OneToMany(mappedBy = "contactUser", cascade = CascadeType.ALL, orphanRemoval = true
    ,fetch = FetchType.EAGER)
    private List<Contact> contacts;

    public User() {
    }

    public User(String username, List<Contact> contacts) {
        this.username = username;
        this.contacts = contacts;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", contacts=" + contacts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && username.equals(user.username) && Objects.equals(contacts, user.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, contacts);
    }
}
