package com.example.parkingSystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.parkingSystem.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectPermByRoleName(@Param("roleName") String roleName);

    List<Map<String, Object>> selectPermByRoleId(@Param("roleId") Integer roleId);

    @Select("select * from role")
    List<Role> showAllRoles();

    List<Role> findAllRolesOfOneUser(@Param("userId") Long userId);

    @Update("UPDATE role SET del_flag = 0 WHERE role_id = #{roleId}")
    int restoreRoleById(@Param("roleId") Integer roleId);
}
