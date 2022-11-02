package com.ttps.backend.controllers;

import com.ttps.backend.models.Categoria;
import com.ttps.backend.models.Response;
import com.ttps.backend.services.EmprendimientoService;
import com.ttps.backend.services.UserService;
import com.ttps.backend.services.implementations.CategoriaServiceImpl;

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
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaServiceImpl categoriaService;
    private final EmprendimientoService emprendimientoService;
    private final UserService userService;

    @GetMapping("/categoria/list")
    public ResponseEntity<Response> getCategorias() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("categorias", categoriaService.list(30)))
                        .message("Categorias retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping("/categoria/get/{id}")
    public ResponseEntity<Response> getCategoria(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("categoria", categoriaService.get(id)))
                        .message("Categoria retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PostMapping("/categoria/save")
    public ResponseEntity<Response> saveCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("categoria", categoriaService.create(categoria)))
                        .message("Categoria created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @DeleteMapping("/categoria/delete/{id}")
    public ResponseEntity<Response> deleteCategoria(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("categoria", categoriaService.delete(id)))
                        .message("Categoria deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PutMapping("/categoria/update")
    public ResponseEntity<Response> updateCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("categoria", categoriaService.update(categoria)))
                        .message("Categoria updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping("/emprendimiento/categoria/list")
    public ResponseEntity<Response> getEmprendimientoCategorias() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "categorias",
                                        userService
                                                .getUser(user)
                                                .getEmprendimiento()
                                                .getCategorias()))
                        .message("Categorias retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PostMapping("/emprendimiento/categoria/save")
    public ResponseEntity<Response> saveEmprendimientoCategoria(@RequestBody Categoria categoria) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "wasSaved",
                                        emprendimientoService.addCategoriaToEmprendimiento(
                                                idEmprendimiento, categoria)))
                        .message("Categoria saved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping("/emprendimiento/categoria/delete/{idCategoria}")
    public ResponseEntity<Response> deleteEmprendimientoCategoria(
            @PathVariable("idCategoria") Long idCategoria) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "wasDeleted",
                                        emprendimientoService.removeCategoriaFromEmprendimiento(
                                                idEmprendimiento, idCategoria)))
                        .message("Categoria deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
