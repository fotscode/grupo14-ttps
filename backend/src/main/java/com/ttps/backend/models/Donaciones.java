package com.ttps.backend.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Donaciones {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String nombrePersona;
    private String contacto; // opcional si es plan
    private Date fecha;
    private int cantidad;
    private double monto;
    private String mensaje;
    @ManyToOne
    private MetodoDonacion metodo;
}
