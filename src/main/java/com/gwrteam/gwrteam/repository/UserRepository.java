package com.gwrteam.gwrteam.repository;

import com.gwrteam.gwrteam.model.User;
import com.gwrteam.gwrteam.web.dto.UserRegistrationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    User findByEmail(String email);
    User findByCustId(String custId);
    User findByPasswordCode(String password);
    User findByVerificationCode(String code);
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = true WHERE u.id = ?1")
    void enabled(Long id);

}
