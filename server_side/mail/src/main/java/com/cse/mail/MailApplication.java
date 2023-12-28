package com.cse.mail;


import com.cse.mail.dal.model.Attachment;
import com.cse.mail.dal.model.Contact;
import com.cse.mail.dal.model.Email;
import com.cse.mail.dal.model.Folder;
import com.cse.mail.dal.model.User;
import com.cse.mail.dal.repository.EmailRepository;
import com.cse.mail.dal.repository.FolderRepository;
import com.cse.mail.dal.repository.UserRepository;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//import java.util.concurrent.TimeUnit;



@SpringBootApplication
public class MailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }
    private Faker faker = new Faker();

    private FolderRepository folderRepository;
    private UserRepository userRepository;
    private EmailRepository emailRepository;

    @Bean
    CommandLineRunner commandLineRunner(FolderRepository folderRepository, UserRepository userRepository, EmailRepository emailRepository){
        this.folderRepository = folderRepository;
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
        return args -> {
            List<User> users = genUsers(4);
            userRepository.saveAll(users);
            for (User user:users) {
                List<Folder> folders = genFolders(faker.number().numberBetween(0,4),user);
                folderRepository.saveAll(folders);
            }

            for (User user:users) {
                List<Email> emails =genSentEmails(faker.number().numberBetween(3,10),user,users);
                List<Email> emailsssss = emailRepository.findAll();
                System.out.println("done user");
            }

            System.out.println("done");
        //    User createdUser = userRepository.save(user);
            // createdUser.getContacts().remove(1);
            // userRepository.save(createdUser);
//            List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "username"));

//            System.out.println(createdUser.getContacts());
        };
    }

    public List<User> genUsers(int amount){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            User user = new User();
            user.setUsername(faker.name().username());
            List<Contact> contacts = new ArrayList<>();
            int numberOfContacts = faker.number().numberBetween(0, 10);
            for (int j = 0; j < numberOfContacts; j++) {
                Contact contact = new Contact();
                contact.setName(faker.name().fullName());
                contact.setContactUser(user);
                contacts.add(contact);
            }
            user.setContacts(contacts);
            users.add(user);
        }

        return users;
    }

    @Transactional
    public List<Folder> genFolders(int extraAmount, User owner){
        List<Folder> folders = new ArrayList<>();
        List<String> defaultFolders = Arrays.asList("Inbox", "Sent", "Trash");
        for (String folderName : defaultFolders) {
            Folder folder = new Folder();
            folder.setFolderName(folderName);
            folder.setRemovable(false);
            folder.setRenamable(false);
            folder.setUser(owner);
            folders.add(folder);
        }
        for(int i = 0; i < extraAmount; i++){
            Folder folder = new Folder();
            folder.setFolderName(faker.lorem().word());
            folder.setRemovable(true);
            folder.setRenamable(true);
            folder.setUser(owner);
            folders.add(folder);
        }
        return folders;
    }
    @Transactional
    public List<Email> genSentEmails(int amount, User sender, List<User> allUsers){
        List<Email> emails = new ArrayList<>();
        for (int j = 0; j < amount; j++) {
            Email email = new Email();
            email.setSubject(faker.lorem().sentence());
            String body = faker.lorem().paragraph();
            if (body.length() > 1000) {
                body = body.substring(0, 1000);
            }
            email.setBody(body);
            email.setTimestamp(LocalDateTime.now());
            email.setDraft(faker.random().nextDouble() < 0.1);
            email.setPriority(faker.number().numberBetween(1, 5));

            List<Attachment> attachments = new ArrayList<>();
            for (int k = 0; k < faker.number().numberBetween(1, 5); k++) {
                Attachment attachment = new Attachment();
                attachment.setFilename(faker.file().fileName());
                attachment.setPath(faker.file().fileName());
                attachments.add(attachment);
            }
            email.setAttachments(attachments);

            
            email.setSender(sender);

            List<User> otherUsers = new ArrayList<>(allUsers);
            otherUsers.remove(sender);
            Collections.shuffle(otherUsers);
            List<User> receivers = otherUsers.subList(0, faker.number().numberBetween(1, otherUsers.size()));
            email.setReceivers(receivers);

            emails.add(email);
            emailRepository.save(email);

            if(email.isDraft()) {
                continue;
            }
//            List<Folder> folders = new ArrayList<Folder>();
            // Add the email to the sender's "Sent" folder
            Folder senderSentFolder = folderRepository.findByFolderNameAndUser_Id("Sent", sender.getId());
            senderSentFolder.getEmails().add(email);
            folderRepository.save(senderSentFolder);
//            folders.add(senderSentFolder);

            // Add the email to the receivers' "Inbox"
            for (User receiver : receivers) {
                Folder receiverInbox = folderRepository.findByFolderNameAndUser_Id("Inbox", receiver.getId());
//                folders.add(receiverInbox);
                receiverInbox.getEmails().add(email);
                folderRepository.save(receiverInbox);
            }
//            email.setFolders(folders);
//            emailRepository.save(email);
//            List<Email> emailsssss = emailRepository.findAll();
        }
        return emails;
    }

    public void customize(ConfigurableWebServerFactory factory) {
        // Set the desired port
        factory.setPort(8000);
    }

}



