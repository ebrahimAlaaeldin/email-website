package com.cse.mail.dal.repository;

import com.cse.mail.dal.model.Email;
import com.cse.mail.dal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmailRepository extends JpaRepository<Email, Integer> {
    @Query("""
            select (count(e) > 0) from Email e left join e.receivers receivers
            where e.id = ?1 and (e.sender.id = ?2 or receivers.id = ?2)""")
    boolean existsByEmailIdAndSender_IdOrReceivers_Id(int id, int userId);


}
