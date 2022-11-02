package com.ttps.backend.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.ttps.backend.models.AppUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken = null;
        try {
            AppUser user = new Gson().fromJson(request.getReader(), AppUser.class);
            log.info("user is {}, pw is {}", user.getEmail(), user.getPassword());
            authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        } catch (JsonSyntaxException | JsonIOException | IOException e) {
            e.printStackTrace();
        }

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication)
            throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        // "secret" deberia ser una private key, evito para no complejizar
        String access_token =
                JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(
                                new Date(
                                        System.currentTimeMillis() + 1000 * 60 * 1000)) // 1000 mins
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(
                                "roles",
                                user.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList()))
                        .sign(algorithm);
        String refresh_token =
                JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(
                                new Date(System.currentTimeMillis() + 600 * 60 * 1000)) // 600 mins
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(
                                "roles",
                                user.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList()))
                        .sign(algorithm);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Origin", "*");
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
