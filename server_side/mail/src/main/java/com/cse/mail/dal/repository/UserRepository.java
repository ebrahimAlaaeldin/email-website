package com.cse.mail.dal.repository;

import com.cse.mail.dal.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where upper(u.username) = upper(:username)")
    User findByUsernameIgnoreCase(@Param("username") @NonNull String username);



}
