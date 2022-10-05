package com.ttps.backend.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class MetodoDonacion {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String titulo; // preguntar titulo con id emprendimiento
    private String descripcion;
    private double monto;
    @OneToMany
    private List<Donaciones> donaciones;
}
