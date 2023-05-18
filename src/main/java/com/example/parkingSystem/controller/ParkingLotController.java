package com.example.parkingSystem.controller;

import com.example.parkingSystem.entity.ParkingLot;
import com.example.parkingSystem.entity.Period;
import com.example.parkingSystem.entity.ResponseResult;
import com.example.parkingSystem.jsr303.InsertOperation;
import com.example.parkingSystem.service.ParkingLotService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@SuppressWarnings("rawtypes")
@Api
@Validated
@RestController()
@RequestMapping("/parkingLot")
public class ParkingLotController {

    @Resource
    private ParkingLotService parkingLotService;

    private static final Logger logger = LoggerFactory.getLogger(ParkingLotController.class);

    @ApiOperation("后台")
    @PreAuthorize(value = "hasAuthority('sys:manager')")
    @GetMapping("/showAllParkingLots")
    public ResponseResult showAllParkingLots(){
        return new ResponseResult<>(200,"操作成功", parkingLotService.showAllParkingLots());
    }

    @PreAuthorize(value = "hasAuthority('sys:get')")
    @ApiOperation("返回所有停车场信息")
    @GetMapping("/findAllParkingLot")
    public ResponseResult findAllParkingLot(){
        return new ResponseResult<List>(200,"操作成功", parkingLotService.findAllParkingLot());
    }

    @PreAuthorize(value = "hasAuthority('sys:get')")
    @ApiOperation("根据id查找停车场")
    @GetMapping("/findParkingLotById/{id}")
    public ResponseResult findParkingLotById(@PathVariable Long id){
        ParkingLot parkingLot = parkingLotService.findParkingLotById(id);
        if(Objects.isNull(parkingLot))
            return new ResponseResult(200,"停车场不存在");
        else
            return new ResponseResult<>(200, "操作成功",parkingLot);
    }

    @PreAuthorize(value = "hasAuthority('sys:post')")
    @ApiOperation("根据请求体中的经纬度键值对查询停车场信息{'longitude': *****}")
    @PostMapping("/findParkingLotByPosition")
    public ResponseResult findParkingLotByPosition(@RequestBody Map<String,Double> map){
        Double longitude = map.get("longitude");
        Double latitude = map.get("latitude");
        ParkingLot parkingLot = parkingLotService.findParkingLotByPosition(longitude, latitude);
        if(Objects.isNull(parkingLot))
            return new ResponseResult(200,"该位置未查询到停车场");
        else
            return new ResponseResult<>(200,"操作成功", parkingLot);
    }

    @ApiOperation("返回所有该类型的停车场")
    @GetMapping("/findParkingLotByCategory/{category}")
    public ResponseResult findParkingLotByCategory(@PathVariable String category){
        return new ResponseResult<>(200, "操作成功",parkingLotService.findParkingLotByCategory(category));
    }

    @PreAuthorize(value = "hasAuthority('sys:get')")
    @ApiOperation("根据id返回停车场经纬度, map形式")
    @GetMapping("/findPositionById/{id}")
    public ResponseResult findPositionById(@PathVariable Long id){
        Map<String,Double> map = parkingLotService.findPositionById(id);
        if(Objects.isNull(map))
            return new ResponseResult(200,"停车场不存在");
        else
            return new ResponseResult<Map>(200, "操作成功", map);
    }


    @PreAuthorize(value = "hasAuthority('sys:manager')")
    @ApiOperation("插入一条停车场信息, 经纬度不得为空, 并且不能与数据库中已有的重复")
    @PostMapping("/insertParkingLot")
    public ResponseResult insertParkingLot(@RequestBody @Valid @Validated(value = {InsertOperation.class}) ParkingLot parkingLot){
        ParkingLot parkingLotByPosition = parkingLotService.findParkingLotByPosition(parkingLot.getLongitude(), parkingLot.getLatitude());
        if(!Objects.isNull(parkingLotByPosition))
            return new ResponseResult(200, "该位置已存在停车场");
        int res = parkingLotService.insertParkingLot(parkingLot);
        Map<String, Integer> map = new HashMap<>();
        map.put("影响行数", res);
        return new ResponseResult<Map>(200,"操作成功", map);
    }

    @PreAuthorize(value = "hasAuthority('sys:manager')")
    @ApiOperation("根据停车场实体类id更新相应的信息，增量更新")
    @PutMapping("/updateParkingLotById")
    public ResponseResult updateParkingLotById(@RequestBody @Valid ParkingLot parkingLot){
        int res = parkingLotService.updateParkingLotById(parkingLot);
        Map<String, Integer> map = new HashMap<>();
        map.put("影响行数", res);
        return new ResponseResult<Map>(200,"操作成功", map);
    }

    @PreAuthorize(value = "hasAuthority('sys:manager')")
    @ApiOperation("全量更新")
    @PutMapping("/setParkingLotById")
    public ResponseResult setParkingLotById(@RequestBody @Valid ParkingLot parkingLot){
        int res = parkingLotService.setParkingLotById(parkingLot);
        Map<String, Integer> map = new HashMap<>();
        map.put("影响行数", res);
        return new ResponseResult<Map>(200,"操作成功", map);
    }

    @PreAuthorize(value = "hasAuthority('sys:manager')")
    @ApiOperation("根据id删除停车场")
    @DeleteMapping("/deleteParkingLotById/{id}")
    public ResponseResult deleteParkingLotById(@PathVariable Long id){
        int res = parkingLotService.deleteParkingLotById(id);
        Map<String, Integer> map = new HashMap<>();
        map.put("影响行数", res);
        return new ResponseResult<Map>(200,"操作成功", map);
    }

    @PreAuthorize("hasAuthority('sys:user')")
    @ApiOperation("指定停车场id,计算该停车场所有的车位数量,包括被占车位")
    @GetMapping("/countAllSpace/{id}")
    public ResponseResult countAllSpace(@PathVariable Long id){
        Map<String, Integer> map = new HashMap<>();
        map.put("车位数量", parkingLotService.countAllSpaceOfParkingLot(id));
        return new ResponseResult<>(200,"操作成功", map);
    }

    @PreAuthorize("hasAuthority('sys:user')")
    @ApiOperation("指定停车场id,返回该停车场的计费规则")
    @GetMapping("/findChargeRuleByParkingLotId/{id}")
    public ResponseResult findChargeRuleByParkingLotId(@PathVariable Long id){
        List<Map<String, Object>> rule = parkingLotService.findChargeRuleByParkingLotId(id);
        if(rule.isEmpty())
            return new ResponseResult<>(200,"未查询到结果，请检查参数");
        else
            return new ResponseResult<>(200,"操作成功",rule);
    }


    @PreAuthorize("hasAuthority('sys:user')")
    @ApiOperation("根据停车场ID,返回此停车场的所有评论信息")
    @GetMapping("/findAllCommentByParkingLotId/{id}")
    public ResponseResult findAllCommentByParkingLotId(@PathVariable Long id){
        return new ResponseResult<>(200,"获取评论成功", parkingLotService.findAllCommentByParkingLotId(id));
    }

    @PreAuthorize("hasAuthority('sys:user')")
    @ApiOperation("返回指定时间段所有停车场可预约的车位数量")
    @PostMapping("/checkAllAvailableParkingLotDuringPeriod")
    public ResponseResult checkAllAvailableParkingLotDuringPeriod(@RequestBody Period period) {
        return new ResponseResult<>(200, "操作成功", parkingLotService.checkAllAvailableParkingLotDuringPeriod(period.getBeginTime(), period.getEndTime()));
    }

    @PreAuthorize("hasAuthority('sys:manager')")
    @GetMapping("/restoreParkingLotById/{id}")
    public ResponseResult restoreParkingLotById(@PathVariable Long id){
        int res = parkingLotService.restoreParkingLotById(id);
        Map<String, Integer> map = new HashMap<>();
        map.put("影响行数",res);
        return new ResponseResult<>(200,"操作成功", map);
    }
}
