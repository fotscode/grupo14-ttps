package com.ttps.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.backend.models.Plan;

@Repository
public interface PlanRepo extends JpaRepository<Plan, Long> {
}
