package com.ttps.backend.services.implementations;

import com.ttps.backend.models.RedSocial;
import com.ttps.backend.repositories.RedSocialRepo;
import com.ttps.backend.services.RedSocialService;

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
public class RedSocialServiceImpl implements RedSocialService {
    private final RedSocialRepo redSocialRepo;

    @Override
    public RedSocial get(Long id) {
        log.info("Obtener redSocial con id: {}", id);
        return redSocialRepo.findById(id).orElse(null);
    }

    @Override
    public Collection<RedSocial> list(int limit) {
        log.info("Listando redSociales");
        return redSocialRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public RedSocial create(RedSocial redSocial) {
        log.info("Guardando nuevo redSocial: {}", redSocial.getNombre());
        return redSocialRepo.save(redSocial);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando redSocial con id: {}", id);
        redSocialRepo.deleteById(id);
        return true;
    }

    @Override
    public RedSocial update(RedSocial redSocial) {
        log.info("Actualizando redSocial con nombre:{}", redSocial.getNombre());
        return redSocialRepo.save(redSocial);
    }
}
