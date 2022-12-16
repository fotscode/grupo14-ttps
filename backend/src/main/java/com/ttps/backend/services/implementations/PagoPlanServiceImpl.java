package com.ttps.backend.services.implementations;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ttps.backend.models.PagoPlan;
import com.ttps.backend.repositories.PagoPlanRepo;
import com.ttps.backend.services.PagoPlanService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class PagoPlanServiceImpl implements PagoPlanService {
    private final PagoPlanRepo pagoPlanRepo;

    @Override
    public PagoPlan get(Long id) {
        log.info("Obtener pago de un plan con id: {}", id);
        return pagoPlanRepo.findById(id).orElse(null);
    }

    @Override
    public Collection<PagoPlan> list(int limit, int page) {
        log.info("Listando pagos de planes");
        return pagoPlanRepo.findAll(PageRequest.of(page, limit)).toList();
    }

    @Override
    public PagoPlan create(PagoPlan pagoPlan) {
        log.info("Guardando nuevo pagoPlan de la persona: {}", pagoPlan.getNombrePersona());
        return pagoPlanRepo.save(pagoPlan);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando manguit con id: {}", id);
        PagoPlan pagoPlan = pagoPlanRepo.findById(id).orElse(null);
        if (pagoPlan == null) {
            return false;
        }
        pagoPlanRepo.deleteById(id);
        return true;
    }

}
