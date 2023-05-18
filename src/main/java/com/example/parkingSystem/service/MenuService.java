package com.example.parkingSystem.service;

import com.example.parkingSystem.entity.Menu;
import com.example.parkingSystem.entity.Role;
import com.example.parkingSystem.entity.RoleMenu;

import java.util.List;
import java.util.Map;

public interface MenuService {
    List<String> selectPermByUserId(Long userId);

    List<Map<String,Object>> showAllRolePermission();

    List<Menu> showPermissionMenu();

    List<Menu> findPermissionMenu();

    List<Menu> findAllPermissionOfOneRole(Integer roleId);

    int insertRoleMenu(RoleMenu roleMenu);

    int deleteRoleMenu(RoleMenu roleMenu);

    int updateMenuById(Menu menu);

    int deleteMenuById(Integer menuId);

    int insertMenu(Menu menu);

    int restoreMenuById(Integer menuId);
}
