package com.ttps.backend.controllers;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttps.backend.models.Emprendimiento;
import com.ttps.backend.models.Response;
import com.ttps.backend.services.implementations.EmprendimientoServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/empredimiento")
@RequiredArgsConstructor
public class EmprendimientoController {
    private final EmprendimientoServiceImpl emprendimientoService;

    @GetMapping("/list")
    public ResponseEntity<Response> getEmprendimientos() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("emprendimientos", emprendimientoService.list(30)))
                        .message("Emprendimientos retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getEmprendimiento(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("emprendimiento", emprendimientoService.get(id)))
                        .message("Emprendimiento retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/get/domain/{domainUrl}")
    public ResponseEntity<Response> getEmprendimiento(@PathVariable("domainUrl") String domainUrl) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("emprendimiento", emprendimientoService.get(domainUrl)))
                        .message("Emprendimiento retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveEmprendimiento(@RequestBody Emprendimiento emprendimiento) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("emprendimiento", emprendimientoService.create(emprendimiento)))
                        .message("Emprendimiento created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteEmprendimiento(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("wasDeleted", emprendimientoService.delete(id)))
                        .message("Emprendimiento deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Response> updateEmprendimiento(@RequestBody Emprendimiento emprendimiento) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("emprendimiento", emprendimientoService.update(emprendimiento)))
                        .message("Emprendimiento updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

}
