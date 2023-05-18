package com.example.parkingSystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "停车位实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Space {

    @ApiModelProperty("停车位ID")
    @TableId(type = IdType.AUTO)
    private Integer spaceId;

    @ApiModelProperty(value = "停车场ID", notes = "外键，表示该停车位所在的停车场",position = 1)
    private Long parkingLotId;

    @ApiModelProperty(value = "停车位编号", notes = "格式: B202",position = 2)
    private String spaceNum;

    @ApiModelProperty(value = "逻辑删除标识",hidden = true)
    private Integer delFlag;

    @Version
    @ApiModelProperty(value = "乐观锁版本",hidden = true)
    private Integer version;
}
