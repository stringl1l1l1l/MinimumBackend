package com.example.parkingSystem.controller;

import io.jsonwebtoken.Claims;
import com.example.parkingSystem.entity.Order;
import com.example.parkingSystem.entity.ResponseResult;
import com.example.parkingSystem.jsr303.InsertOperation;
import com.example.parkingSystem.service.OrderService;
import com.example.parkingSystem.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("rawtypes")
@Api
@Validated
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PreAuthorize("hasAuthority('sys:user')")
    @ApiOperation("当前用户下一个订单, 设置预定时间和停车场后查找是否有空闲车位")
    @PostMapping("/makeOneOrder")
    public ResponseResult makeOneOrder(@RequestHeader("token") String token, @RequestBody @Valid @Validated(value = {InsertOperation.class}) Order order){
        if(order.getEndTime().compareTo(order.getBeginTime()) <= 0)
            return new ResponseResult<>(400, "终止时间早于起始时间");
        try {
            Claims claims = JwtUtil.parseJWT(token);
            order.setUserId(Long.parseLong(claims.getSubject()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int res = orderService.makeOneOrder(order);
        if(res == -1)
            return new ResponseResult(200, "未查询到内容:id错误或无空闲车位");
        else {
            Map<String, Integer> map = new HashMap<>();
            map.put("预计费用", order.getCost());
            return new ResponseResult<Map>(200, "操作成功", map);
        }
    }

    @PreAuthorize("hasAuthority('sys:user')")
    @GetMapping("/cancelOneOrderById/{id}")
    @ApiOperation("根据当前订单id取消此订单,前提是当前时间在预计开始时间前一段时间")
    public ResponseResult cancelOneOrderById(@RequestHeader("token") String token, @PathVariable Long id){
        try {
            Claims claims = JwtUtil.parseJWT(token);
            Long userId = Long.parseLong(claims.getSubject());
            int res = orderService.cancelOneOrderById(userId, id);
            Map<String, Integer> map = new HashMap<>();
            map.put("影响行数", res);
            if(res == 0)
                return new ResponseResult<Map>(200,"订单无法取消,时间已过");
            else
                return new ResponseResult<Map>(200,"操作成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(400,"token解析异常");
        }
    }


    @ApiOperation("根据id删除一条订单信息")
    @PreAuthorize("hasAuthority('sys:user')")
    @DeleteMapping("/deleteOrderById/{id}")
    public ResponseResult deleteOrderById(@PathVariable Long id){
        int res = orderService.deleteOrderById(id);
        Map<String, Integer> map = new HashMap<>();
        map.put("影响行数", res);
        return new ResponseResult<Map>(200,"操作成功", map);
    }

    @ApiOperation("返回指定id的用户的所有订单")
    @PreAuthorize("hasAuthority('sys:user')")
    @GetMapping("/findOrdersByUserId/{userId}")
    public ResponseResult findOrdersByUserId(@PathVariable Long userId){
        return new ResponseResult<>(200, "操作成功", orderService.findAllOrderByUserId(userId));
    }


    @ApiOperation("返回所有订单信息")
    @PreAuthorize("hasAuthority('sys:manager')")
    @GetMapping("/findAllOrder")
    public ResponseResult findAllOrder() {
        return new ResponseResult<>(200, "操作成功", orderService.findAllOrder());
    }
//    @PreAuthorize("hasAuthority('sys:user')")
//    @GetMapping("/calculateCost")
//    @ApiOperation(value = "calculateCost",notes = "根据订单信息计算费用")
//    public ResponseResult calculateCost(@RequestBody @Validated(value = {InsertOperation.class}) Order order){
//        return new ResponseResult<>(200, "操作成功", orderService.calculateCost(order));
//    }
}
