package com.ttps.backend.services.implementations;

import com.ttps.backend.models.AppUser;
import com.ttps.backend.models.Categoria;
import com.ttps.backend.models.Emprendimiento;
import com.ttps.backend.models.Manguito;
import com.ttps.backend.models.Plan;
import com.ttps.backend.models.Post;
import com.ttps.backend.models.RedSocial;
import com.ttps.backend.models.Role;
import com.ttps.backend.repositories.EmprendimientoRepo;
import com.ttps.backend.repositories.RoleRepo;
import com.ttps.backend.repositories.UserRepo;
import com.ttps.backend.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.util.codec.binary.Base64;
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
        Emprendimiento e =
                new Emprendimiento(
                        null,
                        user.getEmail(),
                        "Emprendimiento",
                        "Descripcion",
                        100,
                        true,
                        true,
                        Base64.decodeBase64(
                                "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"),
                        new ArrayList<Categoria>(),
                        new ArrayList<Post>(),
                        new ArrayList<Plan>(),
                        new ArrayList<RedSocial>(),
                        new ArrayList<Manguito>());
        emprendimientoRepo.save(e);
        user.setEmprendimiento(e);

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
        if (user==null || role==null) {
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
