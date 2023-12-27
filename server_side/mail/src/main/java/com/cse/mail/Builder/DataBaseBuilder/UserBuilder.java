package com.cse.mail.Builder.DataBaseBuilder;

import java.util.List;

import com.cse.mail.dal.model.Contact;
import com.cse.mail.dal.model.User;

public class UserBuilder {
    
    private String username;
    private List<Contact> contacts;
    private int userID;

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }
    public UserBuilder setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }
    public UserBuilder setUserID(int userID){
        this.userID = userID;
        return this;
    }

    public User build() {
        return new User(username,userID,contacts);
    }
}
