package com.ttps.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ttps.backend.models.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {
}
