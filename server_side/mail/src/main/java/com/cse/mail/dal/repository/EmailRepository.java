package com.cse.mail.dal.repository;

import com.cse.mail.dal.model.Email;
import com.cse.mail.dal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Integer> {


}
