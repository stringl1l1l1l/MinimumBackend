package com.example.parkingSystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.parkingSystem.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermByUserId(Long userId);

    List<Map<String,Object>> showAllRolePermission();

    @Select("select * from menu")
    List<Menu> showPermissionMenu();

    List<Menu> findAllPermissionOfOneRole(@Param("roleId") Integer roleId);

    @Update("UPDATE menu SET del_flag = 0 WHERE menu_id = #{menuId}")
    int restoreMenuById(@Param("menuId") Integer menuId);
}
