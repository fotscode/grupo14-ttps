package com.ttps.backend.services;

import java.util.Collection;

import com.ttps.backend.models.RedSocial;

public interface RedSocialService {
    RedSocial get(Long id);

    Collection<RedSocial> list(int limit);

    RedSocial create(RedSocial redSocial);

    RedSocial update(RedSocial redSocial);

    Boolean delete(Long id);
}