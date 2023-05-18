package com.example.parkingSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.parkingSystem.entity.Order;
import com.example.parkingSystem.entity.Space;
import com.example.parkingSystem.mapper.OrderMapper;
import com.example.parkingSystem.mapper.ParkingLotMapper;
import com.example.parkingSystem.service.OrderService;
import com.example.parkingSystem.service.SpaceService;
import com.example.parkingSystem.util.JexlUtil;
import com.example.parkingSystem.util.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Transactional
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private SpaceService spaceService;

    @Resource
    private ParkingLotMapper parkingLotMapper;

    @Resource
    private RedisCache redisCache;

    @Override
    public List<Order> findAllOrderByUserId(Long userId) {
        return orderMapper.selectList(new LambdaQueryWrapper<Order>().eq(Order::getUserId,userId));
    }

    @Override
    public List<Order> findAllOrderOfOneParkingLot(Long parkingLotId) {
        return orderMapper.selectList(new LambdaQueryWrapper<Order>().eq(Order::getParkingLotId,parkingLotId));
    }

    @Override
    public List<Order> findAllOrder() {
        return orderMapper.selectList(null);
    }

    @Override
    public int calculateCost(Order order) {
        //尝试取得缓存中的计费表
        List<Map<String, Object>> chargeRule;
        Object cacheObject = redisCache.getCacheObject("parkingLotRule:" + order.getParkingLotId().toString());
        if(Objects.isNull(cacheObject))
            chargeRule = parkingLotMapper.findChargeRuleByParkingLotId(order.getParkingLotId());
        else
            chargeRule = (List<Map<String, Object>>) cacheObject;

        Duration duration = Duration.between(order.getBeginTime(), order.getEndTime());
        long diff = duration.toHours();
        long day = 0;
        if(diff % 24 == 0)
            day = diff/24;
        else
            day= diff/24 + 1;
        //找到天数对应的条件
        for (Map<String, Object> stringObjectMap : chargeRule) {
            Object operator = stringObjectMap.get("operator");
            Object val = stringObjectMap.get("val");
            Map<String,Object> map = new HashMap<>();
            map.put("val",val);
            String express =  Long.toString(day) + operator + " val ";
            //使用jexl解析字符串为java表达式
            Boolean res = (Boolean) JexlUtil.convertToCode(express,map);
            if(res){
                int cost = (int)day * (int)stringObjectMap.get("unit_price");
                order.setCost(cost);
                return cost;
            }
        }
        return -1;
    }

    @Override
    public List<Order> findAllOrderOfOneParkingLotDuringPeriod(Long parkingLotId,LocalDateTime beginTime, LocalDateTime endTime) {
        return orderMapper.selectList(
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getParkingLotId,parkingLotId)
                        .eq(Order::getCancelFlag,0)
                        .lt(Order::getBeginTime,endTime)
                        .gt(Order::getEndTime,beginTime)
                );
    }

    @Override
    public List<Order> findAllOrderDuringPeriod(LocalDateTime beginTime, LocalDateTime endTime) {
        return orderMapper.selectList(
                new LambdaQueryWrapper<Order>()
                        .eq(Order::getCancelFlag,0)
                        .lt(Order::getBeginTime,endTime)
                        .gt(Order::getEndTime,beginTime)
        );
    }

    @Override
    public int makeOneOrder(Order order) {

        List<Map<String, Object>> chargeRule = parkingLotMapper.findChargeRuleByParkingLotId(order.getParkingLotId());
        redisCache.setCacheObject("parkingLotRule:" + order.getParkingLotId().toString(),chargeRule);
        List<Space> spaces = spaceService.checkEmptySpaceDuringPeriodById(order.getParkingLotId(), order.getBeginTime(), order.getEndTime());
        //若指定的停车场在预定时间段有空闲车位
        if(!spaces.isEmpty()){
            //选择第一个空闲车位
            order.setSpaceId(spaces.get(0).getSpaceId());
            order.setCost(calculateCost(order));
            return orderMapper.insert(order);
        }
        else
            return -1;
    }

    @Override
    public int cancelOneOrderById(Long userId, Long id) {
        return orderMapper.update(null,
                new LambdaUpdateWrapper<Order>()
                        .eq(Order::getOrderId,id)
                        .eq(Order::getUserId, userId)
                        .gt(Order::getBeginTime, LocalDateTime.now())
                        .set(Order::getCancelFlag,1)
        );
    }

    @Override
    public int deleteOrderById(Long id) {
        return orderMapper.deleteById(id);
    }

    @Override
    public int restoreOneOrderById(Long userId, Long id) {
        return orderMapper.update(null,
                new LambdaUpdateWrapper<Order>()
                        .eq(Order::getOrderId,id)
                        .eq(Order::getUserId, userId)
                        .eq(Order::getCancelFlag,1)
                        .gt(Order::getBeginTime, LocalDateTime.now())
                        .set(Order::getCancelFlag,0)
        );
    }
}