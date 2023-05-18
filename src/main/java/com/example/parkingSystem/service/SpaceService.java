package com.example.parkingSystem.service;

import com.example.parkingSystem.entity.Space;

import java.time.LocalDateTime;
import java.util.List;

public interface SpaceService {

    List<Space> checkEmptySpaceDuringPeriodById(Long parkingLotId, LocalDateTime begTime, LocalDateTime endTime);
}
