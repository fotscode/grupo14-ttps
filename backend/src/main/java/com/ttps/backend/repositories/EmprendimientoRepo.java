package com.ttps.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.backend.models.Emprendimiento;

@Repository
public interface EmprendimientoRepo extends JpaRepository<Emprendimiento, Long> {
    Emprendimiento findByDomainUrl(String domainUrl);
}