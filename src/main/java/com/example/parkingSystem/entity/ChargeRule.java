package com.example.parkingSystem.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeRule {

    @TableId(type = IdType.NONE)
    @ApiModelProperty(value = "计费规则ID",notes = "主键1")
    private Integer chargeRuleId;

    @ApiModelProperty(value = "规则条件ID",notes = "主键2",position = 1)
    private Integer conditionId;

    @ApiModelProperty(value = "计费单价,单位: 元/天",position = 2)
    private Integer unitPrice;

    @ApiModelProperty(value = "逻辑删除标识",hidden = true)
    private Integer delFlag;

    @Version
    @ApiModelProperty(value = "乐观锁版本",hidden = true)
    private Integer version;
}
