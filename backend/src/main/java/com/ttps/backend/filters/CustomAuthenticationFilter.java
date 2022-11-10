package com.ttps.backend.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.ttps.backend.helpers.JWTFactory;
import com.ttps.backend.models.AppUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

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
        JWTFactory jwtFactory = new JWTFactory();
        User user = (User) authentication.getPrincipal();
        Map<String, String> tokens = jwtFactory.genTokensFromRequest(user, request);
        response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Origin", "*");
        Map<?, ?> body =
                Map.of(
                        "timeStamp",
                        LocalDateTime.now().toString(),
                        "statusCode",
                        HttpStatus.OK.value(),
                        "status",
                        HttpStatus.OK,
                        "path",
                        "/api/login",
                        "data",
                        Map.of("tokens", tokens));
        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed)
            throws IOException, ServletException {
        response.setContentType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<?, ?> body =
                Map.of(
                        "timeStamp",
                        LocalDateTime.now().toString(),
                        "statusCode",
                        HttpStatus.UNAUTHORIZED.value(),
                        "status",
                        HttpStatus.UNAUTHORIZED,
                        "path",
                        "/api/login",
                        "message",
                        "Invalid credentials");
        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}
