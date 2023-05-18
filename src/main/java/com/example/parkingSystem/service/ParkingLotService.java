package com.example.parkingSystem.service;

import com.example.parkingSystem.entity.Comment;
import com.example.parkingSystem.entity.ParkingLot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ParkingLotService {

    List<ParkingLot> findAllParkingLot();

    ParkingLot findParkingLotById(Long id);

    ParkingLot findParkingLotByPosition(Double longitude, Double latitude);

    Map<String,Double> findPositionById(Long id);

    List<ParkingLot> findParkingLotByCategory(String category);

    List<ParkingLot> findAvailableParkingLot(Map<String, Object> map);

    int insertParkingLot(ParkingLot parkingLot);

    int updateParkingLotById(ParkingLot parkingLot);

    int setParkingLotById(ParkingLot parkingLot);

    int deleteParkingLotById(Long parkingLotId);

    int countAllSpaceOfParkingLot(Long parkingLotId);

    int countEmptySpaceOfParkingLot(Long parkingLotId);

    int restoreParkingLotById(Long parkingLotId);

    List<Map<String, Object>> findChargeRuleByParkingLotId(Long parkingLotId);

    List<Comment> findAllCommentByParkingLotId(Long parkingLotId);

    List<Map<String, Object>> checkAllAvailableParkingLotDuringPeriod(LocalDateTime beginTime, LocalDateTime endTime);

    List<ParkingLot> showAllParkingLots();
}
