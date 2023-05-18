package com.example.parkingSystem.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "停车信息实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingInfo {

    @ApiModelProperty("停车信息ID")
    @TableId(type = IdType.AUTO)
    private Long parkingInfoId;

    @ApiModelProperty(value = "用户ID", notes = "外键，表示拥有该条停车信息的用户",position = 1)
    private Long orderId;

    @ApiModelProperty(value = "到达车位时间",position = 3)
    private Date arrivingTime;

    @ApiModelProperty(value = "离开车位时间", notes = "未离开为NULL",position = 4)
    private Date leavingTime;

    @ApiModelProperty(value = "逻辑删除标识",hidden = true,position = 5)
    private Integer delFlag;

    @Version
    @ApiModelProperty(value = "乐观锁版本",hidden = true,position = 6)
    private Integer version;
}
