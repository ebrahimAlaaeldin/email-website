package com.cse.mail.dal.model;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity(name = "Contact")
@Table(name = "contacts")
public class Contact {

    @Id
    @SequenceGenerator(
            name = "contact_sequence",
            sequenceName = "contact_sequence",
            allocationSize =  1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "contact_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private int id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User contactUser;

    @ElementCollection
    private List<String> contactEmails;

    public Contact() {
    }

    public Contact(String name, User contactUser, List<String> contactEmails) {
        this.name = name;
        this.contactUser = contactUser;
        this.contactEmails = contactEmails;
    }

    public List<String> getContactEmails() {
        return contactEmails;
    }

    public void setContactEmails(List<String> contactEmails) {
        this.contactEmails = contactEmails;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactUser(User contactUser) {
        this.contactUser = contactUser;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getContactUser() {
        return contactUser;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactUser=" + contactUser +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return id == contact.id && name.equals(contact.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
