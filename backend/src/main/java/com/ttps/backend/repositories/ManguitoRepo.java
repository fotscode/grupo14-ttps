package com.ttps.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.backend.models.Manguito;

@Repository
public interface ManguitoRepo extends JpaRepository<Manguito, Long> {
}
