package com.example.parkingSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.parkingSystem.entity.Menu;
import com.example.parkingSystem.entity.RoleMenu;
import com.example.parkingSystem.mapper.MenuMapper;
import com.example.parkingSystem.mapper.RoleMenuMapper;
import com.example.parkingSystem.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Transactional
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<String> selectPermByUserId(Long userId) {
       return menuMapper.selectPermByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> showAllRolePermission() {
        return menuMapper.showAllRolePermission();
    }

    @Override
    public List<Menu> findPermissionMenu() {
        return menuMapper.selectList(null);
    }

    @Override
    public List<Menu> findAllPermissionOfOneRole(Integer roleId) {
        return menuMapper.findAllPermissionOfOneRole(roleId);
    }

    @Override
    public List<Menu> showPermissionMenu() {
        return menuMapper.showPermissionMenu();
    }

    @Override
    public int insertRoleMenu(RoleMenu roleMenu) {
        return roleMenuMapper.insert(roleMenu);
    }

    @Override
    public int deleteRoleMenu(RoleMenu roleMenu) {
        return roleMenuMapper.delete(
                new LambdaQueryWrapper<RoleMenu>()
                        .eq(RoleMenu::getMenuId, roleMenu.getMenuId())
                        .eq(RoleMenu::getRoleId, roleMenu.getRoleId())
        );
    }

    @Override
    public int updateMenuById(Menu menu) {
        return menuMapper.updateById(menu);
    }

    @Override
    public int deleteMenuById(Integer menuId) {
        return menuMapper.deleteById(menuId);
    }

    @Override
    public int insertMenu(Menu menu) {
        return menuMapper.insert(menu);
    }

    @Override
    public int restoreMenuById(Integer menuId) {
        return menuMapper.restoreMenuById(menuId);
    }
}
