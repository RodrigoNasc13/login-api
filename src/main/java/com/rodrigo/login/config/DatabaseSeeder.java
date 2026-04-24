package com.rodrigo.login.config;

import com.rodrigo.login.user.model.User;
import com.rodrigo.login.user.model.UserRole;
import com.rodrigo.login.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String superAdminEmail = "admin@admin.com";

        if (!userRepository.existsByEmail(superAdminEmail)) {
            log.info("Creating initial Super Admin user...");

            String hashedPassword = passwordEncoder.encode("admin123");

            User superAdmin = new User(
                    "Super Admin",
                    superAdminEmail,
                    hashedPassword,
                    UserRole.SUPER_ADMIN
            );

            userRepository.save(superAdmin);

            log.info("Super Admin user created successfully with email: {}", superAdminEmail);
        } else {
            log.info("Super Admin user already exists. Skipping database seeding.");
        }
    }
}

