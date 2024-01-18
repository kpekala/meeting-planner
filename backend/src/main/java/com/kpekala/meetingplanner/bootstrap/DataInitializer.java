package com.kpekala.meetingplanner.bootstrap;

import com.kpekala.meetingplanner.domain.user.Role;
import com.kpekala.meetingplanner.domain.user.RoleRepository;
import com.kpekala.meetingplanner.domain.user.User;
import com.kpekala.meetingplanner.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final TransactionTemplate transactionTemplate;

    @Override
    public void run(String... args) {
        setUpRoles();
        setUpUsers();
    }

    private void setUpRoles() {
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");

        roleRepository.saveAll(List.of(userRole, adminRole));
    }

    private void setUpUsers() {
        User user1 = new User("test@test.pl", "$2a$10$u8cAgG/nfRMm2QKeQXF0fOTxMOQMvVp44NN6.Y5XLZVXEWQ3GeqZu");

        user1.setRoles(Set.of(roleRepository.findByName("admin").get()));

        userRepository.save(user1);
    }
}
