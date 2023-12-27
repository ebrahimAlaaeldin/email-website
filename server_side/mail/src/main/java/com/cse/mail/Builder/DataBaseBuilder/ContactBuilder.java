package com.cse.mail.Builder.DataBaseBuilder;

import com.cse.mail.dal.model.Contact;
import com.cse.mail.dal.model.User;
import java.util.List;

public class ContactBuilder {
    private String name;
    private  User contactUser;
    private List<String> contactEmails;
     

    public ContactBuilder setName(String name) {
        this.name = name;
        return this;
    }
    public ContactBuilder setContactUser(User contactUser) {
        this.contactUser = contactUser;
        return this;
    }
    public ContactBuilder setContactEmails(List<String> contactEmails) {
        this.contactEmails = contactEmails;
        return this;
    }

    public Contact build() {
        return new Contact(name, contactUser, contactEmails);
    }



    

    
}
