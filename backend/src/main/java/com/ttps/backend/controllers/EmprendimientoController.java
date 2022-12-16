package com.ttps.backend.controllers;

import com.ttps.backend.models.Emprendimiento;
import com.ttps.backend.models.Manguito;
import com.ttps.backend.models.PagoPlan;
import com.ttps.backend.models.Response;
import com.ttps.backend.services.EmprendimientoService;
import com.ttps.backend.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emprendimiento")
@RequiredArgsConstructor
@Slf4j
public class EmprendimientoController {
    private final EmprendimientoService emprendimientoService;
    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<Response> getEmprendimientos(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam String category,
            @RequestParam String search) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "emprendimientos",
                                        emprendimientoService.list(limit, page, category, search),
                                        "length",
                                        emprendimientoService.getEmprendimientosCount(
                                                category, search)))
                        .message("Emprendimientos retornados")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/emprendimiento/list")
                        .build());
    }

    @GetMapping("/top")
    public ResponseEntity<Response> getEmprendimientosTop() {
        List<Emprendimiento> topManguitos =
                emprendimientoService.list(9999, 0, "", "").stream()
                        .filter(
                                emprendimiento ->
                                        emprendimiento.getFilterByManguitos()
                                                && emprendimiento.getManguitos().size() > 0)
                        .sorted(
                                Comparator.comparingInt(
                                        emprendimiento ->
                                                emprendimiento.getManguitos().stream()
                                                        .mapToInt(Manguito::getCantidad)
                                                        .sum()))
                        .limit(5)
                        .toList();
        List<Emprendimiento> topDonaciones =
                emprendimientoService.list(9999, 0, "", "").stream()
                        .filter(
                                emprendimiento ->
                                        emprendimiento.getFilterByDonations()
                                                && emprendimiento.getPlanes().stream()
                                                                .flatMap(p -> p.getPagos().stream())
                                                                .count()
                                                        > 0)
                        .sorted(
                                Comparator.comparingDouble(
                                        emprendimiento ->
                                                emprendimiento.getPlanes().stream()
                                                        .flatMapToDouble(
                                                                p ->
                                                                        p.getPagos().stream()
                                                                                .mapToDouble(
                                                                                        PagoPlan
                                                                                                ::getMonto))
                                                        .sum()))
                        .limit(5)
                        .toList();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("topManguitos", topManguitos, "topDonaciones", topDonaciones))
                        .message("Emprendimientos retornados")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/emprendimiento/top")
                        .build());
    }

    @GetMapping("/get")
    public ResponseEntity<Response> getEmprendimiento() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Emprendimiento de {}", userService.getUser(user));
        Long id = userService.getUser(user).getEmprendimiento().getId();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("emprendimiento", emprendimientoService.get(id)))
                        .message("Emprendimiento retornado")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/emprendimiento/get")
                        .build());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getEmprendimiento(@PathVariable("id") Long id) {
        Emprendimiento e = emprendimientoService.get(id);
        String msg = e != null ? "Emprendimiento retornado" : "Emprendimiento no encontrado";
        HttpStatus status = e != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("emprendimiento", e != null ? e : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/get/" + id)
                                .build());
    }

    @GetMapping("/get/domain/{domainUrl}")
    public ResponseEntity<Response> getEmprendimiento(@PathVariable("domainUrl") String domainUrl) {
        Emprendimiento e = emprendimientoService.get(domainUrl);
        String msg = e != null ? "Emprendimiento retornado" : "Emprendimiento no encontrado";
        HttpStatus status = e != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("emprendimiento", e != null ? e : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/get/" + domainUrl)
                                .build());
    }

    @PostMapping("/save")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> saveEmprendimiento(@RequestBody Emprendimiento emprendimiento) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(
                                        Map.of(
                                                "emprendimiento",
                                                emprendimientoService.create(emprendimiento)))
                                .message("Emprendimiento created")
                                .status(HttpStatus.CREATED)
                                .statusCode(HttpStatus.CREATED.value())
                                .path("/api/emprendimiento/save")
                                .build());
    }

    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> deleteEmprendimiento(@PathVariable("id") Long id) {
        Emprendimiento empr = emprendimientoService.get(id);
        HttpStatus status = empr != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        String message = empr != null ? "Emprendimiento borrado" : "Emprendimiento no encontrado";
        emprendimientoService.delete(id);
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("emprendimiento", empr != null ? empr : false))
                                .message(message)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/delete/" + id)
                                .build());
    }

    @PutMapping("/update")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> updateEmprendimiento(
            @RequestBody Emprendimiento emprendimiento) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "emprendimiento",
                                        emprendimientoService.update(emprendimiento)))
                        .message("Emprendimiento updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/emprendimiento/update")
                        .build());
    }
}
