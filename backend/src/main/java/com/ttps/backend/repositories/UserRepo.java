package com.ttps.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.backend.models.AppUser;

@Repository
public interface UserRepo extends JpaRepository<AppUser,Long>{
  AppUser findByEmail(String email);
}