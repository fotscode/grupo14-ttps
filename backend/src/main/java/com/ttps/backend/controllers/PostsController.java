package com.ttps.backend.controllers;

import com.ttps.backend.models.Post;
import com.ttps.backend.models.Response;
import com.ttps.backend.services.EmprendimientoService;
import com.ttps.backend.services.PostService;
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
@RequestMapping("/api/emprendimiento/post")
@RequiredArgsConstructor
public class PostsController {
    private final UserService userService;
    private final PostService postService;
    private final EmprendimientoService emprendimientoService;

    @GetMapping("/list")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getPosts() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "posts",
                                        userService.getUser(user).getEmprendimiento().getPosts()))
                        .message("Posts retornados")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/emprendimiento/post/list")
                        .build());
    }

    @GetMapping("/get/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getPost(@PathVariable("id") Long id) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Post p =
                userService.getUser(user).getEmprendimiento().getPosts().stream()
                        .filter(post -> post.getId().equals(id))
                        .findFirst()
                        .orElse(null);
        String msg = p != null ? "Post encontrado" : "Post no encontrado";
        HttpStatus status = p != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("post", p != null ? p : false))
                        .message(msg)
                        .status(status)
                        .statusCode(status.value())
                        .path("/api/emprendimiento/post/get/{id}")
                        .build());
    }

    @PostMapping("/save")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> savePost(@RequestBody Post post) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        boolean wasSaved = emprendimientoService.addPostToEmprendimiento(idEmprendimiento, post);
        String msg = wasSaved ? "Post guardado" : "Emprendimiento no encontrado";
        HttpStatus status = wasSaved ? HttpStatus.CREATED : HttpStatus.NOT_FOUND;
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("post", wasSaved ? post : false))
                        .message(msg)
                        .status(status)
                        .statusCode(status.value())
                        .path("/api/emprendimiento/post/save")
                        .build());
    }

    @PutMapping("/update")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> updatePost(@RequestBody Post post) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        boolean wasUpdated = emprendimientoService.addPostToEmprendimiento(idEmprendimiento, post);
        String msg = wasUpdated ? "Post actualizado" : "Emprendimiento no encontrado";
        HttpStatus status = wasUpdated ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("post", wasUpdated ? post : false))
                        .message(msg)
                        .status(status)
                        .statusCode(status.value())
                        .path("/api/emprendimiento/post/update")
                        .build());
    }

    @DeleteMapping("/delete/{idPost}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> deletePost(@PathVariable("idPost") Long idPost) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        Post p = postService.get(idPost);
        boolean wasDeleted =
                emprendimientoService.removePostFromEmprendimiento(idEmprendimiento, idPost);
        String msg = wasDeleted ? "Post eliminado" : "Post no encontrado";
        HttpStatus status = wasDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("post", p != null ? p : false))
                        .message(msg)
                        .status(status)
                        .statusCode(status.value())
                        .path("/api/emprendimiento/post/delete/{idPost}")
                        .build());
    }
}
