package com.example.parkingSystem.service;

import com.example.parkingSystem.entity.ResponseResult;
import com.example.parkingSystem.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


public interface UserService {
    List<User> findAllUsers();

    User findUserById(Long id);

    User findUserByUsername(String username);

    User findUserByPhoneNum(String phoneNum);

    List<User> findUsersByMap(Map<String, Object> map);

    int deleteUserById(Long id);

    int deleteUserByMap(Map<String, Object> map);

    int updateUserById(User user);

    int setUserById(User user);

    int insertUser(User user);

    Map<String ,Object> getUserInfo(String token);

    User parseToken(String token);

    List<String> getUserRoles(String token);

    List<User> showAllUsers();

    User showUserById(Long userId);

    int restoreUserById(Long userId);

    PageInfo<User> showAllUsersByPages(int pageNum, int pageSize);
}
