package com.cse.mail.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.cse.mail.dal.model.Contact} entity
 */
public class ContactDto implements Serializable {
    private final int contactId;
    private final String name;
    private final List<String> contactEmails;

    private final int userId;

    public ContactDto(int contactId, String name, List<String> contactEmails, int userId) {
        this.contactId = contactId;
        this.name = name;
        this.contactEmails = contactEmails;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public int getContactId() {
        return contactId;
    }

    public String getName() {
        return name;
    }

    public List<String> getContactEmails() {
        return contactEmails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactDto)) return false;
        ContactDto that = (ContactDto) o;
        return contactId == that.contactId && userId == that.userId && Objects.equals(name, that.name) && Objects.equals(contactEmails, that.contactEmails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId, name, contactEmails, userId);
    }

    @Override
    public String toString() {
        return "ContactDto{" +
                "contactId=" + contactId +
                ", name='" + name + '\'' +
                ", contactEmails=" + contactEmails +
                ", userId=" + userId +
                '}';
    }
}