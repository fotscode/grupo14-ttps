package com.ttps.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.backend.models.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long>{
  Role findByName(String name);
}