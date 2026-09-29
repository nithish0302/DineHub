package com.dinehub.user.repository;


import com.dinehub.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String userEmail);
    List<User>findByName(String userName);

    User findByEmail(String userEmail);


    void deleteByEmail(String userEmail);
}
