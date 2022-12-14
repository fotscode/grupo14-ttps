package com.ttps.backend.controllers;

import com.google.gson.Gson;
import com.ttps.backend.helpers.JWTFactory;
import com.ttps.backend.models.AppUser;
import com.ttps.backend.models.Response;
import com.ttps.backend.models.Role;
import com.ttps.backend.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** UserController */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final JWTFactory jwtFactory;

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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("user", userService.saveUser(user)))
                                .message("Usuario creado")
                                .status(HttpStatus.CREATED)
                                .statusCode(HttpStatus.CREATED.value())
                                .path("/api/user/save")
                                .build());
    }

    @GetMapping("/user/roles")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> listRoles() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("roles", userService.getUser(user).getRoles()))
                        .message("Roles retornados")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/user/roles")
                        .build());
    }

    @PostMapping("/role/save")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> saveRole(@RequestBody Role role) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
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
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("user", userService.getUser(form.getEmail())))
                                .message(message)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/role/addtouser")
                                .build());
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<Response> refreshToken(
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpStatus status;
        Map<?, ?> data = new HashMap<>();
        Map<String, String> json = new Gson().fromJson(request.getReader(), Map.class);
        String authorizationHeader = "Bearer " + json.get("refresh_token");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                status = HttpStatus.OK;
                data =
                        Map.of(
                                "tokens",
                                jwtFactory.genTokensFromRequest(
                                        userService, request, json.get("refresh_token")));
            } catch (Exception exception) {
                status = HttpStatus.FORBIDDEN;
                data = Map.of("error", exception.getMessage());
            }
        } else {
            status = HttpStatus.BAD_REQUEST;
            data = Map.of("error", "Token is missing");
        }
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(data)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/token/refresh")
                                .build());
    }
}

@Data
class RoleToUserForm {
    private String email;
    private String roleName;
}
