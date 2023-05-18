package com.example.parkingSystem.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper {

    @Select("select introduction, avatar, name from user_info where user_id = #{userId}")
    Map<String, Object> getUserInfo(@Param("userId") Long userId);

    List<String> getUserRoles(@Param("userId") Long userId);


}
