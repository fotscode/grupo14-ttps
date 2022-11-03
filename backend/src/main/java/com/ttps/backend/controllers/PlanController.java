package com.ttps.backend.controllers;

import java.time.LocalDateTime;
import java.util.Map;

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

import com.ttps.backend.models.Plan;
import com.ttps.backend.models.Response;
import com.ttps.backend.services.EmprendimientoService;
import com.ttps.backend.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/emprendimiento/plan")
@RequiredArgsConstructor
public class PlanController {
    private final UserService userService;
    private final EmprendimientoService emprendimientoService;

    @GetMapping("/list")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getPlans() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "planes",
                                        userService.getUser(user).getEmprendimiento().getPlanes()))
                        .message("Plans retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping("/get/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getPlan(@PathVariable("id") Long id) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "plan",
                                        userService
                                                .getUser(user)
                                                .getEmprendimiento()
                                                .getPlanes()
                                                .stream()
                                                .filter(plan -> plan.getId().equals(id))
                                                .findFirst()
                                                .orElse(null)))
                        .message("Plan retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PostMapping("/save")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> savePlan(@RequestBody Plan plan) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "wasSaved",
                                        emprendimientoService.addPlanToEmprendimiento(
                                                idEmprendimiento, plan)))
                        .message("Plan saved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PutMapping("/update")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> updatePlan(@RequestBody Plan plan) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "wasSaved",
                                        emprendimientoService.addPlanToEmprendimiento(
                                                idEmprendimiento, plan)))
                        .message("Plan updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping("/delete/{idPlan}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> deletePlan(@PathVariable("idPlan") Long idPlan) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "wasDeleted",
                                        emprendimientoService.removePlanFromEmprendimiento(
                                                idEmprendimiento, idPlan)))
                        .message("Plan deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
