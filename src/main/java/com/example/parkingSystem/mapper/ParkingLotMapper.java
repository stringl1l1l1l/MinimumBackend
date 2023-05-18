package com.example.parkingSystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.parkingSystem.entity.Comment;
import com.example.parkingSystem.entity.ParkingLot;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ParkingLotMapper extends BaseMapper<ParkingLot> {

   List<Map<String,Object>> findChargeRuleByParkingLotId(@Param("parkingLotId") Long parkingLotId);

   List<Comment> findAllCommentByParkingLotId(@Param("parkingLotId") Long parkingLotId);

   List<Map<String, Object>> checkAllOccupiedParkingLotDuringPeriod(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);

   @Select("select * from parking_lot")
   List<ParkingLot> showAllParkingLots();

   @Update("update parking_lot set del_flag = 0 where parking_lot_id = #{parkingLotId}")
   int restoreParkingLotById(@Param("parkingLotId") Long parkingLotId);
}
