package com.cse.mail.dal.repository;

import com.cse.mail.dal.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FolderRepository extends JpaRepository<Folder, Integer> {
    @Query("select (count(f) > 0) from Folder f where f.id = ?1 and f.user.id = ?2")
    boolean existsByIdAndUser_Id(int id, int id1);

    Folder findByFolderNameAndUser_Id(String folderName, int id);





}
