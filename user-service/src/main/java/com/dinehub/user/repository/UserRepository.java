package com.dinehub.user.repository;

import com.dinehub.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserEmail(String userEmail);

    List<User> findByUserName(String userName);

    User findByUserEmail(String userEmail);

    void deleteByUserEmail(String userEmail);
}