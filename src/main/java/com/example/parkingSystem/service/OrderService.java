package com.example.parkingSystem.service;

import com.example.parkingSystem.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    //在地图上选择需要的停车场，选择停车时间段，返回停车场车位预计状态，并支付

    /**
     * 查询某用户的所有订单信息
     * @param userId 用户id
     * @return 订单信息
     */
    List<Order> findAllOrderByUserId(Long userId);

    List<Order> findAllOrderOfOneParkingLot(Long parkingLotId);

    List<Order> findAllOrderDuringPeriod(LocalDateTime beginTime, LocalDateTime endTime);

    List<Order> findAllOrder();

    List<Order> findAllOrderOfOneParkingLotDuringPeriod(Long userId, LocalDateTime beginTime, LocalDateTime endTime);
    /**
     *下一个订单，即insert,必须先提供parkingLotId
     */
    int makeOneOrder(Order order);

    /**
     * 根据id取消一条订单,解析当前token以确定当前用户id
     * @param id 订单id
     * @return 影响行数
     */
    int cancelOneOrderById(Long userId, Long id);

    int restoreOneOrderById(Long userId, Long id);

    int calculateCost(Order order);

    int deleteOrderById(Long id);
}
