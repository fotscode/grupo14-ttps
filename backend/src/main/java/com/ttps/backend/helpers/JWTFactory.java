package com.ttps.backend.helpers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.ttps.backend.models.AppUser;
import com.ttps.backend.models.Role;
import com.ttps.backend.services.UserService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

public class JWTFactory {
    private static final Algorithm algorithm =
            Algorithm.HMAC256("secret".getBytes()); // deberia ser la private key

    public Map<String, String> genTokensFromRequest(
            UserService userService, HttpServletRequest request, String refreshToken)
            throws JsonSyntaxException, JsonIOException, IOException {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        String username = decodedJWT.getSubject();
        AppUser user = userService.getUser(username);
        String token =
                JWT.create()
                        .withSubject(user.getEmail()) // 1000 mins
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 100))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(
                                "roles",
                                user.getRoles().stream()
                                        .map(Role::getName)
                                        .collect(Collectors.toList()))
                        .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", token);
        tokens.put("refresh_token", refreshToken);
        return tokens;
    }

    public Map<String, String> genTokensFromRequest(User user, HttpServletRequest request) {
        String accessToken =
                JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(
                                new Date(
                                        System.currentTimeMillis() + 2 * 60 * 60 * 1000)) // 2 horas
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(
                                "roles",
                                user.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList()))
                        .sign(algorithm);

        String refreshToken =
                JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(
                                new Date(
                                        System.currentTimeMillis()
                                                + 5 * 24 * 60 * 60 * 1000)) // 5 dias
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(
                                "roles",
                                user.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList()))
                        .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        return tokens;
    }
}
