package com.ttps.backend.controllers;

import com.ttps.backend.models.RedSocial;
import com.ttps.backend.models.Response;
import com.ttps.backend.services.EmprendimientoService;
import com.ttps.backend.services.RedSocialService;
import com.ttps.backend.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/emprendimiento/redes")
@RequiredArgsConstructor
public class RedSocialController {
    private final UserService userService;
    private final RedSocialService redSocialService;
    private final EmprendimientoService emprendimientoService;

    @GetMapping("/list")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getRedesSociales() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "redesSociales",
                                        userService
                                                .getUser(user)
                                                .getEmprendimiento()
                                                .getRedesSociales()))
                        .message("Redes sociales retornadas")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/emprendimiento/redes/list")
                        .build());
    }

    @GetMapping("/get/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getRedSocial(@PathVariable("id") Long id) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        RedSocial r =
                userService.getUser(user).getEmprendimiento().getRedesSociales().stream()
                        .filter(redSocial -> redSocial.getId().equals(id))
                        .findFirst()
                        .orElse(null);
        String msg = r != null ? "Red social retornada" : "No se encontro la red social";
        HttpStatus status = r != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("redSocial", r != null ? r : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/redes/get/" + id)
                                .build());
    }

    @PostMapping("/save")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> saveRedSocial(@RequestBody RedSocial redSocial) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        boolean wasSaved =
                emprendimientoService.addRedSocialToEmprendimiento(idEmprendimiento, redSocial);
        String msg = wasSaved ? "Red social guardada" : "Emprendimiento no encontrado";
        HttpStatus status = wasSaved ? HttpStatus.CREATED : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("redSocial", wasSaved ? redSocial : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/redes/save")
                                .build());
    }

    @PutMapping("/update")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> updateRedSocial(@RequestBody RedSocial redSocial) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        boolean wasUpdated =
                emprendimientoService.addRedSocialToEmprendimiento(idEmprendimiento, redSocial);
        String msg = wasUpdated ? "Red social actualizada" : "Red social no encontrada";
        HttpStatus status = wasUpdated ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("redSocial", wasUpdated ? redSocial : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/redes/update")
                                .build());
    }

    @DeleteMapping("/delete/{idRedSocial}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> deleteRedSocial(@PathVariable("idRedSocial") Long idRedSocial) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        RedSocial r = redSocialService.get(idRedSocial);
        boolean wasDeleted =
                emprendimientoService.removeRedSocialFromEmprendimiento(
                        idEmprendimiento, idRedSocial);
        String msg = wasDeleted ? "Red social eliminada" : "Red social no encontrada";
        HttpStatus status = wasDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("redSocial", r != null ? r : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/redes/delete/" + idRedSocial)
                                .build());
    }
}
