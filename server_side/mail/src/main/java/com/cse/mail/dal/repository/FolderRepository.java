package com.cse.mail.dal.repository;

import com.cse.mail.dal.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Integer> {

}
