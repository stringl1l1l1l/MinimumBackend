package com.example.parkingSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private Long userInfoId;

    private Long userId;

    private String introduction;

    @URL(message = "请输入URL")
    private String avatar;

    private String name;

    private List<String> roles;
}
