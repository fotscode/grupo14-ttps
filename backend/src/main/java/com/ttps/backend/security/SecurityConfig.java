package com.ttps.backend.security;

import com.ttps.backend.filters.CustomAuthenticationFilter;
import com.ttps.backend.filters.CustomAuthorizationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/** SecurityConfig */
@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .antMatchers(
                        HttpMethod.POST,
                        "/api/login/",
                        "/api/user/**",
                        "/api/emprendimiento/manguito/**",
                        "/api/emprendimiento/plan/pago/**")
                .permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/api/emprendimiento/list",
                        "/api/emprendimiento/get/**",
                        "/api/token/**",
                        "/api/categoria/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/emprendimiento/**")
                .hasAnyAuthority("ROLE_USER")
                .antMatchers(HttpMethod.POST, "/api/emprendimiento/**")
                .hasAnyAuthority("ROLE_USER")
                .antMatchers(HttpMethod.PUT, "/api/emprendimiento/**")
                .hasAnyAuthority("ROLE_USER")
                .antMatchers(
                        HttpMethod.DELETE,
                        "/api/emprendimiento/categoria/**",
                        "/api/emprendimiento/post/**",
                        "/api/emprendimiento/plan/**",
                        "/api/emprendimiento/redes/**")
                .hasAnyAuthority("ROLE_USER")
                .antMatchers(HttpMethod.GET, "/api/users")
                .hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/api/role/**", "/api/categoria/**")
                .hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/categoria/**")
                .hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(
                        HttpMethod.DELETE, "/api/categoria/**", "/api/emprendimiento/delete/**")
                .hasAnyAuthority("ROLE_ADMIN")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(
                        new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .httpBasic();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedHeader("Origin");
        corsConfiguration.addAllowedHeader("X-Requested-With");
        corsConfiguration.addAllowedHeader("Content-Type");
        corsConfiguration.addAllowedHeader("Accept");
        corsConfiguration.addAllowedHeader("Authorization");
        corsConfiguration.addAllowedHeader("X-PINGOTHER");
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        // TODO change when deploying
        corsConfiguration.addAllowedOrigin("http://localhost:4200");
        corsConfiguration.setMaxAge(1728000L);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
