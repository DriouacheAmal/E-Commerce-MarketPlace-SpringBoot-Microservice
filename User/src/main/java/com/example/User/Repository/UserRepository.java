package com.example.User.Repository;

import com.example.User.Entity.User;
import com.example.User.enums.Active;
import com.example.User.enums.UserRole;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>findByEmail(String email);

    Optional<User> findByUsername(String username);
    Optional<User> findByConfirmationToken(String token);
   // Optional<User> findbyUserRole(UserRole userRole);

    List<User> findByActive(Active active);

    boolean existsByEmail(String email);

    Optional<User> findByResetPasswordToken(String token);

    UserRole findUserRoleByResetPasswordToken(String token);

   /* @Modifying
    @Query("UPDATE UserRole u SET u.password = :password WHERE u.resetPasswordToken = :token")
    void updatePasswordByResetPasswordToken(@Param("token") String token, @Param("password") String password);

    */

    User findByUserRole(UserRole userRole);
}

