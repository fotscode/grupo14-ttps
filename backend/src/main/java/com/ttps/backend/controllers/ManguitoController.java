package com.ttps.backend.controllers;

import com.ttps.backend.models.Emprendimiento;
import com.ttps.backend.models.Manguito;
import com.ttps.backend.models.Response;
import com.ttps.backend.services.EmprendimientoService;
import com.ttps.backend.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emprendimiento/manguito")
@RequiredArgsConstructor
public class ManguitoController {
    private final UserService userService;
    private final EmprendimientoService emprendimientoService;

    @GetMapping("/list")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getManguitos(@RequestParam int page, @RequestParam int limit) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Manguito> manguitos = userService.getUser(user).getEmprendimiento().getManguitos();
        int start = page * limit;
        int end = Math.min(start + limit, manguitos.size());
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "manguitos",
                                        manguitos.subList(start, end),
                                        "length",
                                        manguitos.size()))
                        .message("Manguitos retornados")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/emprendimiento/manguito/list")
                        .build());
    }

    @GetMapping("/get/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getManguito(@PathVariable("id") Long id) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Manguito mang =
                userService.getUser(user).getEmprendimiento().getManguitos().stream()
                        .filter(manguito -> manguito.getId().equals(id))
                        .findFirst()
                        .orElse(null);
        HttpStatus status = mang != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        String msg = mang != null ? "Manguito retornado" : "Manguito no encontrado";
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("manguito", mang != null ? mang : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/manguito/get/" + id)
                                .build());
    }

    @PostMapping("/save/{idEmprendimiento}")
    public ResponseEntity<Response> saveManguito(
            @PathVariable("idEmprendimiento") Long idEmprendimiento,
            @RequestBody Manguito manguito) {
        Emprendimiento emprendimiento = emprendimientoService.get(idEmprendimiento);
        manguito.setMonto(emprendimiento.getValorManguito());
        boolean wasSaved =
                emprendimientoService.addManguitoToEmprendimiento(idEmprendimiento, manguito);
        HttpStatus status = wasSaved ? HttpStatus.CREATED : HttpStatus.NOT_FOUND;
        String msg = wasSaved ? "Manguito guardado" : "Emprendimiento no encontrado";
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("manguito", wasSaved ? manguito : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/manguito/save/" + idEmprendimiento)
                                .build());
    }
}
