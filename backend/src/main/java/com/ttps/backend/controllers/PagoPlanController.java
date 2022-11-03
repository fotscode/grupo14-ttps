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

import com.ttps.backend.models.PagoPlan;
import com.ttps.backend.models.Response;
import com.ttps.backend.services.PlanService;
import com.ttps.backend.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/emprendimiento/plan/pago")
@RequiredArgsConstructor
public class PagoPlanController {
    private final UserService userService;
    private final PlanService planService;

    @GetMapping("/list")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> getPagoPlans() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(
                                Map.of(
                                        "pagosPlanes",
                                        userService
                                                .getUser(user)
                                                .getEmprendimiento()
                                                .getPlanes()
                                                .stream()
                                                .flatMap(p -> p.getPagos().stream())))
                        .message("Pagos a planes retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PostMapping("/save/{idPlan}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Response> savePagoPlan(@PathVariable("idPlan") Long idPlan,@RequestBody PagoPlan pagoPlan) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("wasSaved", planService.addPagoToPlan(idPlan, pagoPlan)))
                        .message("Pago Plan saved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
