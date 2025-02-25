package com.luicode.keysy.keysyservice.repositories;


import com.luicode.keysy.keysyservice.entities.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPasswordRepository extends JpaRepository<UserPassword, Long> {

    List<UserPassword> findByUserId(Long userId);

    Optional<UserPassword> findByIdAndUserId(Long id, Long userId);
}
