package com.example.parkingSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.parkingSystem.entity.Comment;
import com.example.parkingSystem.entity.ParkingLot;
import com.example.parkingSystem.entity.Space;
import com.example.parkingSystem.mapper.CommentMapper;
import com.example.parkingSystem.mapper.ParkingLotMapper;
import com.example.parkingSystem.mapper.SpaceMapper;
import com.example.parkingSystem.service.ParkingLotService;
import com.example.parkingSystem.service.SpaceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Transactional
@Service("parkingLotService")
public class ParkingLotServiceImpl implements ParkingLotService {

    @Resource
    private ParkingLotMapper parkingLotMapper;

    @Resource
    private SpaceMapper spaceMapper;

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Map<String, Object>> findChargeRuleByParkingLotId(Long parkingLotId) {
        return parkingLotMapper.findChargeRuleByParkingLotId(parkingLotId);
    }

    @Override
    public List<ParkingLot> showAllParkingLots() {
        return parkingLotMapper.showAllParkingLots();
    }

    @Override
    public List<ParkingLot> findAllParkingLot() {
        return parkingLotMapper.selectList(null);
    }

    @Override
    public ParkingLot findParkingLotById(Long parkingLotId) {
        return parkingLotMapper.selectById(parkingLotId);
    }

    @Override
    public ParkingLot findParkingLotByPosition(Double longitude, Double latitude) {
        return parkingLotMapper.selectOne(
                new LambdaQueryWrapper<ParkingLot>()
                        .eq(ParkingLot::getLongitude,longitude)
                        .eq(ParkingLot::getLatitude, latitude)
        );
    }

    @Override
    public List<ParkingLot> findAvailableParkingLot(Map<String, Object> map) {
        return null;
    }

    @Override
    public Map<String, Double> findPositionById(Long parkingLotId) {
        ParkingLot parkingLot = this.findParkingLotById(parkingLotId);
        if(Objects.isNull(parkingLot))
            return null;
        Map<String,Double> map = new HashMap<>();
        map.put("longitude",parkingLot.getLongitude());
        map.put("latitude",parkingLot.getLatitude());
        return map;
    }

    @Override
    public List<Map<String, Object>> checkAllAvailableParkingLotDuringPeriod(LocalDateTime beginTime, LocalDateTime endTime) {
        List<Map<String, Object>> maps = parkingLotMapper.checkAllOccupiedParkingLotDuringPeriod(beginTime, endTime);
        for (Map<String, Object> map: maps) {
            int parking_lot_id =  (Integer)map.get("parking_lot_id");
            long total = countAllSpaceOfParkingLot( (long) parking_lot_id);
            map.replace("space_count", total - (long) map.get("space_count"));
        }
        return maps;
    }

    @Override
    public List<ParkingLot> findParkingLotByCategory(String category) {
        return parkingLotMapper.selectList(
                new LambdaQueryWrapper<ParkingLot>()
                    .eq(ParkingLot::getCategory,category)
        );
    }

    @Override
    public int insertParkingLot(ParkingLot parkingLot) {
        return parkingLotMapper.insert(parkingLot);
    }

    @Override
    public int updateParkingLotById(ParkingLot parkingLot) {
        return parkingLotMapper.updateById(parkingLot);
    }

    @Override
    public List<Comment> findAllCommentByParkingLotId(Long parkingLotId) {
        return parkingLotMapper.findAllCommentByParkingLotId(parkingLotId);
    }

    @Override
    public int restoreParkingLotById(Long parkingLotId) {
        return parkingLotMapper.restoreParkingLotById(parkingLotId);
    }

    @Override
    public int setParkingLotById(ParkingLot parkingLot) {
        return parkingLotMapper.update(parkingLot,
                new LambdaUpdateWrapper<ParkingLot>()
                        .set(ParkingLot::getLongitude,parkingLot.getLongitude())
                        .set(ParkingLot::getLatitude,parkingLot.getLatitude())
                        .set(ParkingLot::getCategory,parkingLot.getCategory())
                        .set(ParkingLot::getDescription,parkingLot.getDescription())
                        .set(ParkingLot::getChargeRuleId,parkingLot.getChargeRuleId())
                        .set(ParkingLot::getDelFlag,parkingLot.getDelFlag())
                        .eq(ParkingLot::getParkingLotId,parkingLot.getParkingLotId())
        );
    }

    @Override
    public int deleteParkingLotById(Long parkingLotId) {
        return parkingLotMapper.deleteById(parkingLotId);
    }

    @Override
    public int countAllSpaceOfParkingLot(Long parkingLotId) {
        return spaceMapper.selectCount(
                new LambdaQueryWrapper<Space>()
                        .eq(Space::getParkingLotId, parkingLotId)
        );
    }

    @Override
    public int countEmptySpaceOfParkingLot(Long parkingLotId) {
        return 0;
    }
}
