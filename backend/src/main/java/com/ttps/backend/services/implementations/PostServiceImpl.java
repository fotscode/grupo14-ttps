package com.ttps.backend.services.implementations;

import com.ttps.backend.models.Post;
import com.ttps.backend.repositories.PostRepo;
import com.ttps.backend.services.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;

    @Override
    public Post get(Long id) {
        log.info("Obtener post con id: {}", id);
        return postRepo.findById(id).orElse(null);
    }

    @Override
    public Collection<Post> list(int limit, int page) {
        log.info("Listando posts");
        return postRepo.findAll(PageRequest.of(page, limit)).toList();
    }

    @Override
    public Post create(Post post) {
        log.info("Guardando nuevo post: {}", post.getTitulo());
        return postRepo.save(post);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Borrando post con id: {}", id);
        Post post = postRepo.findById(id).orElse(null);
        if (post == null) {
            return false;
        }
        postRepo.deleteById(id);
        return true;
    }

    @Override
    public Post update(Post post) {
        log.info("Actualizando post con titulo:{}", post.getTitulo());
        return postRepo.save(post);
    }
}
