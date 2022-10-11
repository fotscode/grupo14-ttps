package com.ttps.backend.models;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Emprendimiento {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String domainUrl;
    private String nombre;
    private String descripcion;
    private double valorManguito;
    private Boolean filterByDonations;
    private Boolean filterByManguitos;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imagen;
    @OneToOne()
    private AppUser appUser;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Categoria> categorias;
    @OneToMany
    private List<Post> posts;
    @OneToMany
    private List<Plan> planes;
    @OneToMany
    private List<RedSocial> redesSociales;
    @OneToMany
    private List<Manguito> manguitos;
}
