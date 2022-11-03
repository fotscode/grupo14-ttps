package com.ttps.backend.controllers;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttps.backend.models.Manguito;
import com.ttps.backend.models.Response;
import com.ttps.backend.services.EmprendimientoService;
import com.ttps.backend.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/emprendimiento/manguito")
@RequiredArgsConstructor
public class ManguitoController {
    private final UserService userService;
    private final EmprendimientoService emprendimientoService;

    @GetMapping("/list")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getManguitos() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "manguitos",
                                        userService
                                                .getUser(user)
                                                .getEmprendimiento()
                                                .getManguitos()))
                        .message("Manguitos retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping("/get/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getManguito(@PathVariable("id") Long id) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "manguito",
                                        userService
                                                .getUser(user)
                                                .getEmprendimiento()
                                                .getManguitos()
                                                .stream()
                                                .filter(manguito -> manguito.getId().equals(id))
                                                .findFirst()
                                                .orElse(null)))
                        .message("Manguito retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PostMapping("/save")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> saveManguito(@RequestBody Manguito manguito) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "wasSaved",
                                        emprendimientoService.addManguitoToEmprendimiento(
                                                idEmprendimiento, manguito)))
                        .message("Manguito saved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
