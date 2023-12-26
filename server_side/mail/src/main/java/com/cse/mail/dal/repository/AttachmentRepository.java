package com.cse.mail.dal.repository;

import com.cse.mail.dal.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {


}
