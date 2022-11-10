package com.ttps.backend.controllers;

import com.ttps.backend.models.Plan;
import com.ttps.backend.models.Response;
import com.ttps.backend.services.EmprendimientoService;
import com.ttps.backend.services.PlanService;
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
@RequestMapping("/api/emprendimiento/plan")
@RequiredArgsConstructor
public class PlanController {
    private final UserService userService;
    private final PlanService planService;
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
                        .message("Planes retornados")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/emprendimiento/plan/list")
                        .build());
    }

    @GetMapping("/get/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getPlan(@PathVariable("id") Long id) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Plan p =
                userService.getUser(user).getEmprendimiento().getPlanes().stream()
                        .filter(plan -> plan.getId().equals(id))
                        .findFirst()
                        .orElse(null);
        String msg = p != null ? "Plan encontrado" : "Plan no encontrado";
        HttpStatus status = p != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("plan", p != null ? p : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/plan/get/" + id)
                                .build());
    }

    @PostMapping("/save")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> savePlan(@RequestBody Plan plan) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        boolean wasSaved = emprendimientoService.addPlanToEmprendimiento(idEmprendimiento, plan);
        String msg = wasSaved ? "Plan guardado" : "Emprendimiento no encontrado";
        HttpStatus status = wasSaved ? HttpStatus.CREATED : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("plan", wasSaved ? plan : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/plan/save")
                                .build());
    }

    @PutMapping("/update")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> updatePlan(@RequestBody Plan plan) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        boolean wasUpdated = emprendimientoService.addPlanToEmprendimiento(idEmprendimiento, plan);
        String msg = wasUpdated ? "Plan actualizado" : "Emprendimiento no encontrado";
        HttpStatus status = wasUpdated ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("plan", wasUpdated ? plan : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/plan/update")
                                .build());
    }

    @DeleteMapping("/delete/{idPlan}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> deletePlan(@PathVariable("idPlan") Long idPlan) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long idEmprendimiento = userService.getUser(user).getEmprendimiento().getId();
        boolean wasDeleted =
                emprendimientoService.removePlanFromEmprendimiento(idEmprendimiento, idPlan);
        String msg = wasDeleted ? "Plan eliminado" : "Emprendimiento no encontrado";
        HttpStatus status = wasDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        Plan p = planService.get(idPlan);
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("plan", p != null ? p : false))
                                .message(msg)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/plan/delete/" + idPlan)
                                .build());
    }
}
