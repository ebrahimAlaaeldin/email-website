package com.cse.mail.dal.repository;

import com.cse.mail.dal.model.Contact;
import com.cse.mail.dal.model.Filter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {


}
