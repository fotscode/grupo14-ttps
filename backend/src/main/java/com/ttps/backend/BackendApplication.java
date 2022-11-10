package com.ttps.backend;

import com.ttps.backend.helpers.JWTFactory;
import com.ttps.backend.models.AppUser;
import com.ttps.backend.models.Role;
import com.ttps.backend.services.UserService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Manguito API", version = "1.0", description = "Manguito API"))
public class BackendApplication {
    @Value("${spring.security.user.name}")
    private String name;

    @Value("${spring.security.user.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JWTFactory jwtFactory() {
        return new JWTFactory();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveUser(new AppUser(null, name, password, name, new ArrayList<>(), null));
            userService.addRoleToUser(name, "ROLE_ADMIN");
        };
    }
}
