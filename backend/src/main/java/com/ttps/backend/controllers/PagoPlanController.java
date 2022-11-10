package com.ttps.backend.controllers;

import com.ttps.backend.models.PagoPlan;
import com.ttps.backend.models.Response;
import com.ttps.backend.services.PlanService;
import com.ttps.backend.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

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
                        .message("Pagos a planes retornados")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .path("/api/emprendimiento/plan/pago/list")
                        .build());
    }

    @PostMapping("/save/{idPlan}")
    public ResponseEntity<Response> savePagoPlan(
            @PathVariable("idPlan") Long idPlan, @RequestBody PagoPlan pagoPlan) {
        boolean wasSaved = planService.addPagoToPlan(idPlan, pagoPlan);
        HttpStatus status = wasSaved ? HttpStatus.CREATED : HttpStatus.NOT_FOUND;
        String message = wasSaved ? "Pago a plan creado" : "Pago a plan no creado";
        return ResponseEntity.status(status)
                .body(
                        Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .data(Map.of("pagoPlan", wasSaved ? pagoPlan : false))
                                .message(message)
                                .status(status)
                                .statusCode(status.value())
                                .path("/api/emprendimiento/plan/pago/save/" + idPlan)
                                .build());
    }
}
