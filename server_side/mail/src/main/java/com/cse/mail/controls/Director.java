package com.cse.mail.controls;

import java.util.List;
import java.util.Queue;

// facade design pattern to hide the complexity of the system from the user
public class Director {
    private static Director instance;
    public Director() {

    }
    public User login(String username) {
        System.out.println("Logged in successfully");
        //after doing the function of searching in the database
        User searchedUser = findUser(username);
        if(searchedUser == null) {
            return signup(username);
        }
        return searchedUser;
    }
    public User signup(String username) {
        System.out.println("Signed up successfully");
        User newUser = new User(username);
        return newUser;
    }

    public void sendMail(Email email, User from, List<User> to,) {

        System.out.println("Mail sent successfully");
        for (User user : to)
            SendEmailInDataBase(email, from, user);


    }
    public void copyMail() {

        System.out.println("Mail copied successfully");
    }
    public void deleteMail() {

        System.out.println("Mail deleted successfully");
    }
    public void createfolder() {
        System.out.println("folder created successfully");
    }
    public void deletefolder() {
        System.out.println("folder deleted successfully");
    }
    public void moveMail() {
        System.out.println("Mail moved successfully");
    }
    public void createcontacts() {
        System.out.println("contacts created successfully");
    }
    public void deletecontacts() {
        System.out.println("contacts deleted successfully");
    }
    public void filter() {
        System.out.println("filter applied successfully");
    }
    public void sort() {
        System.out.println("sort applied successfully");
    }

    //complete the methods here
    // of the controller

//    public void logout() {
//        System.out.println("Logged out successfully");
//    }


    public void createFolder() {
        System.out.println("Folder created successfully");
    }
    public void deleteFolder() {
        System.out.println("Folder deleted successfully");
    }
    public void createContacts() {
        System.out.println("Contacts created successfully");
    }
    public void deleteContacts() {
        System.out.println("Contacts deleted successfully");
    }

    public void loadMails() {
        System.out.println("Mails loaded successfully");
    }

    public static Director getInstance() {
        if(Director.class == null)
            return new Director();
        return instance;
    }
}
