package com.spring.ecommerce;

import com.spring.ecommerce.entity.User;
import com.spring.ecommerce.enums.Role;
import com.spring.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class EcommerceApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    public void run(String... args) throws Exception {
        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if(adminAccount == null){
            User user = User.builder()
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@gmail.com")
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(user);
        }
        User managerAccount = userRepository.findByRole(Role.MANAGER);
        if(managerAccount == null){
            User manager = User.builder()
                    .firstName("manager")
                    .lastName("manager")
                    .email("manager@gmail.com")
                    .password(new BCryptPasswordEncoder().encode("manager"))
                    .role(Role.MANAGER)
                    .build();
            userRepository.save(manager);
        }
    }
}
