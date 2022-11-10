package com.ttps.backend.controllers;

import com.ttps.backend.models.Categoria;
import com.ttps.backend.models.Emprendimiento;
import com.ttps.backend.models.Response;
import com.ttps.backend.services.EmprendimientoService;
import com.ttps.backend.services.UserService;
import com.ttps.backend.services.implementations.CategoriaServiceImpl;

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
                        .message("Categorias retornadas")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/categoria/list")
                        .build());
    }

    @GetMapping("/categoria/get/{id}")
    public ResponseEntity<Response> getCategoria(@PathVariable("id") Long id) {
        Categoria c = categoriaService.get(id);
        String msg = c != null ? "Categoria retornada" : "Categoria no encontrada";
        HttpStatus status = c != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("categoria", c != null ? c : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/categoria/get/" + id)
                                .build());
    }

    @PostMapping("/categoria/save")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> saveCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("categoria", categoriaService.create(categoria)))
                                .message("Categoria creada")
                                .status(HttpStatus.CREATED)
                                .statusCode(HttpStatus.CREATED.value())
                                .path("/api/categoria/save")
                                .build());
    }

    @DeleteMapping("/categoria/delete/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> deleteCategoria(@PathVariable("id") Long id) {
        boolean wasDeleted = categoriaService.delete(id);
        Categoria c = categoriaService.get(id);
        HttpStatus status = wasDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        String msg = wasDeleted ? "Categoria borrada" : "Categoria no encontrada";
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("categoria", c != null ? c : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/categoria/delete/" + id)
                                .build());
    }

    @PutMapping("/categoria/update")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> updateCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("categoria", categoriaService.update(categoria)))
                        .message("Categoria actualizada")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/categoria/update")
                        .build());
    }

    @GetMapping("/emprendimiento/categoria/list")
    @SecurityRequirement(name = "Bearer Authentication")
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
                        .message("Categorias retornadas")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/emprendimiento/categoria/list")
                        .build());
    }

    @PostMapping("/emprendimiento/categoria/save/{idCategoria}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> saveEmprendimientoCategoria(
            @PathVariable("idCategoria") Long idCategoria) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Emprendimiento emprendimiento = userService.getUser(user).getEmprendimiento();
        Long idEmprendimiento = emprendimiento.getId();
        boolean wasSaved =
                emprendimientoService.addCategoriaToEmprendimiento(idEmprendimiento, idCategoria);
        Categoria c = categoriaService.get(idCategoria);

        HttpStatus status = wasSaved ? HttpStatus.CREATED : HttpStatus.NOT_FOUND;
        String msg = wasSaved ? "Categoria guardada" : "Categoria no encontrada";

        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("categoria", c != null ? c : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/categoria/save/" + idCategoria)
                                .build());
    }

    @DeleteMapping("/emprendimiento/categoria/delete/{idCategoria}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> deleteEmprendimientoCategoria(
            @PathVariable("idCategoria") Long idCategoria) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Emprendimiento emprendimiento = userService.getUser(user).getEmprendimiento();
        Long idEmprendimiento = emprendimiento.getId();
        boolean wasDeleted =
                emprendimientoService.removeCategoriaFromEmprendimiento(
                        idEmprendimiento, idCategoria);
        String msg = wasDeleted ? "Categoria borrada" : "Categoria no encontrada";
        HttpStatus status = wasDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        Categoria c = categoriaService.get(idCategoria);
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("categoria", c != null ? c : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/categoria/delete/" + idCategoria)
                                .build());
    }
}
