package com.ttps.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ttps.backend.models.RedSocial;

public interface RedSocialRepo extends JpaRepository<RedSocial, Long> {
}