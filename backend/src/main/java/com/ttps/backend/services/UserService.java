package com.ttps.backend.services;

import java.util.List;

import com.ttps.backend.models.AppUser;
import com.ttps.backend.models.Role;

public interface UserService {
  AppUser saveUser(AppUser user);

  Role saveRole(Role role);

  void addRoleToUser(String email, String roleName);

  AppUser getUser(String email);

  List<AppUser> getUsers();
}
