package com.ttps.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ttps.backend.models.PagoPlan;

public interface PagoPlanRepo extends JpaRepository<PagoPlan, Long> {
}
