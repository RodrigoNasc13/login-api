package com.rodrigo.login.user.controller;

import com.rodrigo.login.user.dto.ActiveCountResponse;
import com.rodrigo.login.user.dto.CreateUserRequest;
import com.rodrigo.login.user.dto.UpdateUserRequest;
import com.rodrigo.login.user.dto.UserResponse;
import com.rodrigo.login.user.mapper.UserMapper;
import com.rodrigo.login.user.model.User;
import com.rodrigo.login.user.model.UserRole;
import com.rodrigo.login.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, UserResponse>> getCurrentUser(@AuthenticationPrincipal User user) {

        UserResponse response = userMapper.toResponse(user);
        return ResponseEntity.ok(Map.of("user", response));
    }

    @GetMapping
    public ResponseEntity<Map<String, List<UserResponse>>> getUsers() {

        List<UserResponse> list = userService.getAllUsers()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        return ResponseEntity.ok(Map.of("users", list));
    }

    @GetMapping("/admins")
    public ResponseEntity<Page<UserResponse>> getAdmins(Pageable pageable) {
        Page<UserResponse> admins = userService.getUsersByRole(UserRole.ADMIN, pageable)
                .map(userMapper::toResponse);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/regular")
    public ResponseEntity<Page<UserResponse>> getRegularUsers(Pageable pageable) {
        Page<UserResponse> users = userService.getUsersByRole(UserRole.USER, pageable)
                .map(userMapper::toResponse);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/active/count")
    public ResponseEntity<ActiveCountResponse> getActiveUsersCount() {
        long count = userService.getActiveUsersCount();
        LocalDateTime lastUserRegistrationDate = userService.getLastUserRegistrationDate();

        return ResponseEntity.ok(new ActiveCountResponse(count, lastUserRegistrationDate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, UserResponse>> getUserById(@PathVariable UUID id) {
        User user = userService.getUser(id);
        UserResponse response = userMapper.toResponse(user);

        return ResponseEntity.ok(Map.of("user", response));
    }

    @PostMapping
    public ResponseEntity<Map<String, UserResponse>> registerUser(@Valid @RequestBody CreateUserRequest request) {
        User savedUser = userService.createUser(request.name(), request.email(), request.password());
        UserResponse response = userMapper.toResponse(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "user", response
        ));
    }

    @PostMapping("/admin")
    public ResponseEntity<Map<String, UserResponse>> registerAdmin(@Valid @RequestBody CreateUserRequest request) {
        User savedUser = userService.createAdmin(request.name(), request.email(), request.password());
        UserResponse response = userMapper.toResponse(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "user", response
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, UserResponse>> updateUser(@Valid @RequestBody UpdateUserRequest request, @PathVariable UUID id) {
        User updatedUser = userService.updateUser(id, request.name());
        UserResponse response = userMapper.toResponse(updatedUser);

        return ResponseEntity.ok(Map.of(
                "user", response
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);

        return ResponseEntity.ok(Map.of(
                "message", "User deleted successfully"
        ));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, UserResponse>> toggleUserStatus(@PathVariable UUID id) {
        User result = userService.toggleUserStatus(id);
        UserResponse response = userMapper.toResponse(result);

        return ResponseEntity.ok(Map.of(
                "user", response
        ));
    }
}
