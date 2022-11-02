package com.ttps.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emprendimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String domainUrl;

    private String nombre;
    private String descripcion;
    private double valorManguito;
    private Boolean filterByDonations;
    private Boolean filterByManguitos;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imagen;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Categoria> categorias;

    @OneToMany private List<Post> posts;
    @OneToMany private List<Plan> planes;
    @OneToMany private List<RedSocial> redesSociales;
    @OneToMany private List<Manguito> manguitos;

    public void addCategoria(Categoria categoria) {
        this.categorias.add(categoria);
    }

    public void removeCategoria(Categoria categoria) {
        this.categorias.stream()
                .filter(p -> p.getId() == categoria.getId())
                .findFirst()
                .ifPresent(p -> this.categorias.remove(p));
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void removePost(Post post) {
        this.posts.stream()
                .filter(p -> p.getId() == post.getId())
                .findFirst()
                .ifPresent(p -> this.posts.remove(p));
    }

    public void addPlan(Plan plan) {
        this.planes.add(plan);
    }

    public void removePlan(Plan plan) {
        this.planes.stream()
                .filter(p -> p.getId() == plan.getId())
                .findFirst()
                .ifPresent(p -> this.planes.remove(p));
    }

    public void addRedSocial(RedSocial redSocial) {
        this.redesSociales.add(redSocial);
    }

    public void removeRedSocial(RedSocial redSocial) {
        this.redesSociales.stream()
                .filter(p -> p.getId() == redSocial.getId())
                .findFirst()
                .ifPresent(p -> this.redesSociales.remove(p));
    }

    public void addManguito(Manguito manguito) {
        this.manguitos.add(manguito);
    }

    public void removeManguito(Manguito manguito) {
        this.manguitos.stream()
                .filter(p -> p.getId() == manguito.getId())
                .findFirst()
                .ifPresent(p -> this.manguitos.remove(p));
    }
}
