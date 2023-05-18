package com.example.parkingSystem.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.example.parkingSystem.jsr303.InsertOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(value = "停车场实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {

    @Min(value = 0,message = "id格式错误")
    @ApiModelProperty(value = "停车场ID",position = 1)
    @TableId(type = IdType.AUTO)
    private Long parkingLotId;

    @Max(value = 180,message = "经纬度格式错误")
    @Min(value = 0,message = "经纬度格式错误")
    @NotNull(groups = {InsertOperation.class},message = "位置信息不能为空")
    @ApiModelProperty(value = "停车场所在经度",position = 2)
    private Double longitude;

    @Max(value = 90,message = "经纬度格式错误")
    @Min(value = 0,message = "经纬度格式错误")
    @NotNull(groups = {InsertOperation.class},message = "位置信息不能为空")
    @ApiModelProperty(value = "停车场所在纬度",position = 3)
    private Double latitude;

    @Length(max = 500, message = "内容过长(500字符以内)")
    @ApiModelProperty(value = "停车场简介",position = 4)
    private String description;

    @ApiModelProperty(value = "停车场类型",
            notes = "扩展功能，对停车场所在位置进行分类，如：小区、医院、机场等，默认为NULL",position = 5)
    private String category;


    @ApiModelProperty(value = "计费规则ID",position = 6)
    private Integer chargeRuleId;

    @ApiModelProperty(value = "逻辑删除标识", hidden = true)
    private Integer delFlag;

    @Version
    @ApiModelProperty(value = "乐观锁版本",hidden = true,position = 6)
    private Integer version;
}
