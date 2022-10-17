package com.ttps.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ttps.backend.models.Post;

public interface PostRepo extends JpaRepository<Post, Long> {
}
