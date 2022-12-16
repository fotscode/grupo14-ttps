package com.ttps.backend.services;

import java.util.Collection;

import com.ttps.backend.models.Post;

public interface PostService {
    Post get(Long id);

    Collection<Post> list(int limit, int page);

    Post create(Post post);

    Post update(Post post);

    Boolean delete(Long id);
}



