package com.cse.mail.Builder.DtoBuilder;

import java.util.List;

import com.cse.mail.dto.ContactDto;

public class ContactDtoBuilder {
    
    private   int contactId;
    private   String name;
    private   List<String> contactEmails;

    private   int userId;

    public ContactDtoBuilder setContactId(int contactId) {
        this.contactId = contactId;
        return this;
    }
    public ContactDtoBuilder setName(String name) {
        this.name = name;
        return this;
    }
    public ContactDtoBuilder setContactEmails(List<String> contactEmails) {
        this.contactEmails = contactEmails;
        return this;
    }
    public ContactDtoBuilder setUserId(int userId) {
        this.userId = userId;
        return this;
    }
    public ContactDto build() {
        return new ContactDto(contactId, name, contactEmails, userId);
    }
    
}
