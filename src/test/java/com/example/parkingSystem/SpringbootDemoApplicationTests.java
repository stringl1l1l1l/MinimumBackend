package com.example.parkingSystem;

import com.example.parkingSystem.entity.Order;
import com.example.parkingSystem.entity.ParkingLot;
import com.example.parkingSystem.entity.User;
import com.example.parkingSystem.service.MenuService;
import com.example.parkingSystem.service.OrderService;
import com.example.parkingSystem.service.ParkingLotService;
import com.example.parkingSystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootTest
class SpringbootDemoApplicationTests {

    @Test
    void contextLoads() {
        UserService userService = BeanUtil.getBean(UserService.class);
        ParkingLotService parkingLotService = BeanUtil.getBean(ParkingLotService.class);
        System.out.println(parkingLotService.findAllCommentByParkingLotId(1L));
    }





}
