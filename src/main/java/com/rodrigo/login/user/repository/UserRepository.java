package com.rodrigo.login.user.repository;

import com.rodrigo.login.user.model.User;
import com.rodrigo.login.user.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);
    long countByActiveTrue();
    Optional<User> findByEmail(String email);
    @Query("SELECT MAX(u.createdAt) FROM User u")
    LocalDateTime findLastUserCreationDate();

    Page<User> findAllByRole(UserRole role, Pageable pageable);

}
