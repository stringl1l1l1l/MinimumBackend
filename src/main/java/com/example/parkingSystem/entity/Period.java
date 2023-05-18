package com.example.parkingSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Period {

    @NotNull(message = "参数不能为空")
    @Future(message = "预约时间须晚于当前时间")
    LocalDateTime beginTime;

    @NotNull(message = "参数不能为空")
    @Future(message = "预约时间须晚于当前时间")
    LocalDateTime endTime;
}
