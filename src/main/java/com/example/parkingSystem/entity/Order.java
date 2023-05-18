package com.example.parkingSystem.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.example.parkingSystem.jsr303.InsertOperation;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("订单实体类")
@TableName("orders")
public class Order implements Serializable {
    private static final long serialVersionUID= -1474916124089325019L;

    @ApiModelProperty(value = "订单ID")
    @TableId(type = IdType.AUTO)
    private Long orderId;

    @NotNull(groups = {InsertOperation.class},message = "请设置时间")
    @Future(message = "预定时间须晚于当前时间")
    @ApiModelProperty(value = "预定起始时间",position = 2)
    private LocalDateTime beginTime;

    @NotNull(groups = {InsertOperation.class},message = "请设置时间")
    @Future(message = "预定时间须晚于当前时间")
    @ApiModelProperty(value = "预定终止时间",position = 3)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "用户ID", notes = "外键",hidden = true)
    private Long userId;

    @ApiModelProperty(value = "车位ID,外键",hidden = true)
    private Integer spaceId;

    @NotNull(message = "请选择停车场",groups = {InsertOperation.class})
    @ApiModelProperty(value = "停车场ID,外键",position = 1)
    private Long parkingLotId;

    @Min(0)
    @ApiModelProperty(value = "费用",position = 4)
    private Integer cost;

    @ApiModelProperty(value = "订单是否取消的标识(0:正常, 1:取消),默认为0",hidden = true)
    private Integer cancelFlag;

    @ApiModelProperty(value = "逻辑删除标识",hidden = true)
    private Integer delFlag;

    @Version
    @ApiModelProperty(value = "乐观锁版本",hidden = true)
    private Integer version;
}
