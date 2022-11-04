package com.ttps.backend.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttps.backend.models.AppUser;
import com.ttps.backend.models.Response;
import com.ttps.backend.models.Role;
import com.ttps.backend.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** UserController */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getUsers() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("usuarios", userService.getUsers()))
                        .message("Usuarios retornados")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/users")
                        .build());
    }

    @PostMapping("/user/save")
    public ResponseEntity<Response> saveUser(@RequestBody AppUser user) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("user", userService.saveUser(user)))
                        .message("Usuario creado")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .path("/api/user/save")
                        .build());
    }

    @PostMapping("/role/save")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> saveRole(@RequestBody Role role) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("user", userService.saveRole(role)))
                        .message("Rol creado")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .path("/api/role/save")
                        .build());
    }

    @PostMapping("/role/addtouser")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> addRoleToUser(@RequestBody RoleToUserForm form) {
        boolean wasSaved = userService.addRoleToUser(form.getEmail(), form.getRoleName());
        String message =
                wasSaved ? "Rol agregado al usuario" : "No se pudo agregar el rol al usuario";
        HttpStatus status = wasSaved ? HttpStatus.CREATED : HttpStatus.NOT_FOUND;
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("user", userService.getUser(form.getEmail())))
                        .message(message)
                        .status(status)
                        .statusCode(status.value())
                        .path("/api/role/addtouser")
                        .build());
    }

    @GetMapping("/token/refresh")
    @SecurityRequirement(name = "Bearer Authentication")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm =
                        Algorithm.HMAC256("secret".getBytes()); // deberia ser la private key
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user = userService.getUser(username);
                String access_token =
                        JWT.create()
                                .withSubject(user.getEmail())
                                .withExpiresAt(
                                        new Date(
                                                System.currentTimeMillis()
                                                        + 10 * 60 * 1000)) // 10 mins
                                .withIssuer(request.getRequestURL().toString())
                                .withClaim(
                                        "roles",
                                        user.getRoles().stream()
                                                .map(Role::getName)
                                                .collect(Collectors.toList()))
                                .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                log.error("error logging: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

@Data
class RoleToUserForm {
    private String email;
    private String roleName;
}
