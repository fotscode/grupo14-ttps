package com.ttps.backend.services;

import java.util.Collection;

import com.ttps.backend.models.Categoria;
import com.ttps.backend.models.Emprendimiento;
import com.ttps.backend.models.Manguito;
import com.ttps.backend.models.Plan;
import com.ttps.backend.models.Post;
import com.ttps.backend.models.RedSocial;

public interface EmprendimientoService {
    Emprendimiento get(Long id);

    Emprendimiento get(String domainUrl);

    Collection<Emprendimiento> list(int limit);

    Emprendimiento create(Emprendimiento empredimiento);

    Emprendimiento update(Emprendimiento empredimiento);

    Boolean delete(Long id);

    Boolean addPostToEmprendimiento(Long id, Post post);

    Boolean removePostFromEmprendimiento(Long idEmprendimiento, Long idPost);

    Boolean addCategoriaToEmprendimiento(Long id, Long idCategoria);

    Boolean removeCategoriaFromEmprendimiento(Long idEmprendimiento, Long idCategoria);

    Boolean addPlanToEmprendimiento(Long id, Plan plan);

    Boolean removePlanFromEmprendimiento(Long idEmprendimiento, Long idPlan);

    Boolean addRedSocialToEmprendimiento(Long id, RedSocial redSocial);

    Boolean removeRedSocialFromEmprendimiento(Long idEmprendimiento, Long idRedSocial);

    Boolean addManguitoToEmprendimiento(Long id, Manguito manguito);

    Boolean removeManguitoFromEmprendimiento(Long idEmprendimiento, Long idManguito);
}
