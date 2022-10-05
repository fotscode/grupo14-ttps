package com.ttps.backend.security;
import java.util.List;

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

import com.ttps.backend.filters.CustomAuthenticationFilter;
import com.ttps.backend.filters.CustomAuthorizationFilter;

import lombok.RequiredArgsConstructor;

/**
 * SecurityConfig
 */
@Configuration
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
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
    customAuthenticationFilter.setFilterProcessesUrl("/api/login");
    http.csrf().disable()
        .cors().configurationSource(corsConfigurationSource())
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/login/**").permitAll()
        .and().authorizeRequests()
        .antMatchers(HttpMethod.GET, "/**").permitAll()
        .and().authorizeRequests()
        .antMatchers(HttpMethod.POST, "/**").hasAnyAuthority("ROLE_USER")
        .and().authorizeRequests()
        .antMatchers(HttpMethod.DELETE, "/**").hasAnyAuthority("ROLE_USER")
        .and().authorizeRequests()
        .antMatchers(HttpMethod.PUT, "/**").hasAnyAuthority("ROLE_USER")
        .and().authorizeRequests().anyRequest().authenticated()
        .and().addFilter(customAuthenticationFilter)
        .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        .httpBasic();
    // TODO add routes for admin only
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
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedOrigin("http://localhost:4200");
    corsConfiguration.setMaxAge(1728000L);
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

}