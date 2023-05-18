package com.example.parkingSystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("conditions")
@ApiModel("收费规则条件桩实体类")
public class Condition {

    @TableId(type = IdType.AUTO)
    private Integer conditionId;

    @ApiModelProperty(value = "条件内容,待转为java代码,遵循java语句条件格式", position = 1)
    private String operator;

    @ApiModelProperty(value = "",position = 2)
    private Integer val;

    @ApiModelProperty(value = "逻辑删除标识",hidden = true)
    private Integer delFlag;

    @Version
    @ApiModelProperty(value = "乐观锁版本",hidden = true)
    private Integer version;
}
