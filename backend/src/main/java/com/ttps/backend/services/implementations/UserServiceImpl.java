package com.ttps.backend.services.implementations;

import com.ttps.backend.models.AppUser;
import com.ttps.backend.models.Emprendimiento;
import com.ttps.backend.models.Role;
import com.ttps.backend.repositories.EmprendimientoRepo;
import com.ttps.backend.repositories.RedSocialRepo;
import com.ttps.backend.repositories.RoleRepo;
import com.ttps.backend.repositories.UserRepo;
import com.ttps.backend.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

/** UserServiceImpl */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final EmprendimientoRepo emprendimientoRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final RedSocialRepo redSocialRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("User found: " + user.getEmail());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving user {}", user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>(Arrays.asList(roleRepo.findByName("ROLE_USER"))));
        Emprendimiento emprendimiento = user.getEmprendimiento();
        if (emprendimiento != null) {
            emprendimiento.getRedesSociales().forEach(redSocial -> redSocialRepo.save(redSocial));
            emprendimientoRepo.save(emprendimiento);
        }
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public Boolean addRoleToUser(String email, String roleName) {
        AppUser user = userRepo.findByEmail(email);
        Role role = roleRepo.findByName(roleName);
        if (user == null || role == null) {
            return false;
        }
        user.addRole(role);
        return true;
    }

    @Override
    public AppUser getUser(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("users: {}", userRepo.findAll());
        return userRepo.findAll();
    }
}
