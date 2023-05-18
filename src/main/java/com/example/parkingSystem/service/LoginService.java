package com.example.parkingSystem.service;

import com.example.parkingSystem.entity.ResponseResult;
import com.example.parkingSystem.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult register(User user);

    ResponseResult logout();
}
