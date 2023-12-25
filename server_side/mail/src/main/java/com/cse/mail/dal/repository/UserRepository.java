package com.cse.mail.dal.repository;

import com.cse.mail.dal.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where upper(u.username) like upper(?1) order by u.firstName")
    List<User> findByUsernameLikeIgnoreCaseOrderByFirstNameAsc(@Nullable String username);


    @Override
    List<User> findAll();

    @Query("select u from User u")
    List<User> findByAllIgnoreCase(Pageable pageable);
}
