package com.ttps.backend.services.implementations;

import com.ttps.backend.models.Categoria;
import com.ttps.backend.models.Emprendimiento;
import com.ttps.backend.models.Manguito;
import com.ttps.backend.models.Plan;
import com.ttps.backend.models.Post;
import com.ttps.backend.models.RedSocial;
import com.ttps.backend.repositories.EmprendimientoRepo;
import com.ttps.backend.repositories.ManguitoRepo;
import com.ttps.backend.repositories.PlanRepo;
import com.ttps.backend.repositories.PostRepo;
import com.ttps.backend.repositories.RedSocialRepo;
import com.ttps.backend.services.CategoriaService;
import com.ttps.backend.services.EmprendimientoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmprendimientoServiceImpl implements EmprendimientoService {
    private final EmprendimientoRepo emprendimientoRepo;
    private final PostRepo postRepo;
    private final ManguitoRepo manguitoRepo;
    private final PlanRepo planRepo;
    private final RedSocialRepo redSocialRepo;
    private final CategoriaService categoriaService;

    @Override
    public Emprendimiento get(Long id) {
        log.info("Obteniendo emprendimiento con id:{}", id);
        return emprendimientoRepo.findById(id).orElse(null);
    }

    @Override
    public Emprendimiento get(String domainUrl) {
        log.info("Obteniendo emprendimiento con dominio:{}", domainUrl);
        return emprendimientoRepo.findByDomainUrl(domainUrl).orElse(null);
    }

    @Override
    public Collection<Emprendimiento> list(int limit) {
        log.info("Obteniendo lista de emprendimientos");
        return emprendimientoRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Emprendimiento create(Emprendimiento emprendimiento) {
        log.info("Creando emprendimiento con dominio:{}", emprendimiento.getDomainUrl());
        return emprendimientoRepo.save(emprendimiento);
    }

    @Override
    public Emprendimiento update(Emprendimiento emprendimiento) {
        log.info("Actualizando emprendimiento con dominio:{}", emprendimiento.getDomainUrl());
        return emprendimientoRepo.save(emprendimiento);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando emprendimiento con id:{}", id);
        if (emprendimientoRepo.findById(id).isPresent()) {
            emprendimientoRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Boolean addPostToEmprendimiento(Long id, Post post) {
        Emprendimiento emprendimiento = emprendimientoRepo.findById(id).orElse(null);
        if (post.getId() != null) { // updatear
            emprendimiento.removePost(post);
        }
        log.info("Creando post con titulo:{}", post.getTitulo());
        postRepo.save(post);
        log.info("Agregar post con titulo:{} al emprendimiento con id:{}", post.getTitulo(), id);
        if (emprendimiento != null) {

            emprendimiento.addPost(post);
            return true;
        }
        return false;
    }

    @Override
    public Boolean removePostFromEmprendimiento(Long idEmprendimiento, Long idPost) {
        Emprendimiento emprendimiento = emprendimientoRepo.findById(idEmprendimiento).orElse(null);
        Post post = postRepo.findById(idPost).orElse(null);
        if (emprendimiento != null && post != null) {
            log.info(
                    "Borrando post con titulo:{} del emprendimiento con id:{}",
                    post.getTitulo(),
                    idEmprendimiento);
            emprendimiento.removePost(post);
            postRepo.deleteById(idPost);
            return true;
        }
        return false;
    }

    @Override
    public Boolean addCategoriaToEmprendimiento(Long id, Long idCategoria) {
        Emprendimiento emprendimiento = emprendimientoRepo.findById(id).orElse(null);
        log.info("Buscando categoria con id:{}", idCategoria);
        Categoria categoria = categoriaService.get(idCategoria);
        if (emprendimiento != null && categoria != null) {
            log.info(
                    "Agregar categoria con nombre:{} al emprendimiento con id:{}",
                    categoria.getNombre(),
                    id);
            emprendimiento.addCategoria(categoria);
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeCategoriaFromEmprendimiento(Long idEmprendimiento, Long idCategoria) {
        Emprendimiento emprendimiento = emprendimientoRepo.findById(idEmprendimiento).orElse(null);
        Categoria categoria = categoriaService.get(idCategoria);
        if (emprendimiento != null && categoria != null) {
            log.info(
                    "Borrando categoria con titulo:{} del emprendimiento con id:{}",
                    categoria.getNombre(),
                    idEmprendimiento);
            emprendimiento.removeCategoria(categoria);
            return true;
        }
        return false;
    }

    @Override
    public Boolean addPlanToEmprendimiento(Long id, Plan plan) {
        Emprendimiento emprendimiento = emprendimientoRepo.findById(id).orElse(null);
        if (plan.getId() != null) { // updatear
            emprendimiento.removePlan(plan);
        }
        log.info("Creando plan con nombre:{}", plan.getTitulo());
        planRepo.save(plan);
        log.info("Agregar plan con nombre:{} al emprendimiento con id:{}", plan.getTitulo(), id);
        if (emprendimiento != null) {

            emprendimiento.addPlan(plan);
            return true;
        }
        return false;
    }

    @Override
    public Boolean removePlanFromEmprendimiento(Long idEmprendimiento, Long idPlan) {
        Emprendimiento emprendimiento = emprendimientoRepo.findById(idEmprendimiento).orElse(null);
        Plan plan = planRepo.findById(idPlan).orElse(null);
        if (emprendimiento != null && plan != null) {
            log.info(
                    "Borrando plan con titulo:{} del emprendimiento con id:{}",
                    plan.getTitulo(),
                    idEmprendimiento);
            emprendimiento.removePlan(plan);
            planRepo.deleteById(idPlan);
            return true;
        }
        return false;
    }

    @Override
    public Boolean addRedSocialToEmprendimiento(Long id, RedSocial redSocial) {
        Emprendimiento emprendimiento = emprendimientoRepo.findById(id).orElse(null);
        if (redSocial.getId() != null) { // updatear
            emprendimiento.removeRedSocial(redSocial);
        }
        log.info("Creando red social con nombre:{}", redSocial.getNombre());
        redSocialRepo.save(redSocial);
        log.info(
                "Agregar red social con nombre:{} al emprendimiento con id:{}",
                redSocial.getNombre(),
                id);
        if (emprendimiento != null) {

            emprendimiento.addRedSocial(redSocial);
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeRedSocialFromEmprendimiento(Long idEmprendimiento, Long idRedSocial) {
        Emprendimiento emprendimiento = emprendimientoRepo.findById(idEmprendimiento).orElse(null);
        RedSocial redSocial = redSocialRepo.findById(idRedSocial).orElse(null);
        if (emprendimiento != null && redSocial != null) {
            log.info(
                    "Borrando redSocial con titulo:{} del emprendimiento con id:{}",
                    redSocial.getNombre(),
                    idEmprendimiento);
            emprendimiento.removeRedSocial(redSocial);
            redSocialRepo.deleteById(idRedSocial);
            return true;
        }
        return false;
    }

    @Override
    public Boolean addManguitoToEmprendimiento(Long id, Manguito manguito) {
        Emprendimiento emprendimiento = emprendimientoRepo.findById(id).orElse(null);
        log.info("Creando manguito con nombre de persona:{}", manguito.getNombrePersona());
        if (manguito.getId() != null) { // updatear
            emprendimiento.removeManguito(manguito);
        }
        manguitoRepo.save(manguito);
        log.info(
                "Agregar manguito con nombre de persona:{} al emprendimiento con id:{}",
                manguito.getNombrePersona(),
                id);
        if (emprendimiento != null) {

            emprendimiento.addManguito(manguito);
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeManguitoFromEmprendimiento(Long idEmprendimiento, Long idManguito) {
        Emprendimiento emprendimiento = emprendimientoRepo.findById(idEmprendimiento).orElse(null);
        Manguito manguito = manguitoRepo.findById(idManguito).orElse(null);
        if (emprendimiento != null && manguito != null) {
            log.info(
                    "Borrando manguito con titulo:{} del emprendimiento con id:{}",
                    manguito.getNombrePersona(),
                    idEmprendimiento);
            emprendimiento.removeManguito(manguito);
            manguitoRepo.deleteById(idManguito);
            return true;
        }
        return false;
    }
}
