package com.rodrigo.login.user.service;

import com.rodrigo.login.exception.InactiveUserException;
import com.rodrigo.login.exception.UserNotFoundException;
import com.rodrigo.login.user.model.User;
import com.rodrigo.login.user.model.UserRole;
import com.rodrigo.login.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public Page<User> getUsersByRole(UserRole role, Pageable pageable) {
        return userRepository.findAllByRole(role, pageable);
    }

    public long getActiveUsersCount() {
        return userRepository.countByActiveTrue();
    }

    public LocalDateTime getLastUserRegistrationDate() {
        return userRepository.findLastUserCreationDate();
    }

    public User getUser(UUID id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    public User createUser(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email " + email + " is already in use");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User newUser = new User(name, email, hashedPassword, UserRole.USER);

        return userRepository.save(newUser);
    }

    public User createAdmin(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email " + email + " is already in use");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User newUser = new User(name, email, hashedPassword, UserRole.ADMIN);

        return userRepository.save(newUser);
    }

    public User updateUser(UUID id, String name) {
        User user = getUser(id);

        if (!user.isUserActive())
            throw new InactiveUserException("User " + user.getName() + " is inactive and cannot be updated");

        user.setName(name);
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        User user = getUser(id);

        userRepository.delete(user);
    }

    public User toggleUserStatus(UUID id) {
        User user = getUser(id);
        boolean newStatus = !user.isUserActive();

        user.setUpdatedAt(LocalDateTime.now());
        user.setUserStatus(newStatus);

        return userRepository.save(user);
    }

}
