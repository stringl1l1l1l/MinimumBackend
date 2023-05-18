package com.example.parkingSystem.service;

import com.example.parkingSystem.entity.Role;
import com.example.parkingSystem.entity.UserRole;

import java.util.List;

public interface RoleService {

    List<Role> showAllRoles();

    List<Role> findAllRoles();

    List<Role> findAllRolesOfOneUser(Long userId);

    int insertUserRole(UserRole userRole);

    int deleteUserRole(UserRole userRole);

    int updateRoleById(Role role);

    int deleteRoleById(Integer roleId);

    int insertRole(Role role);

    int restoreRoleById(Integer roleId);
}
