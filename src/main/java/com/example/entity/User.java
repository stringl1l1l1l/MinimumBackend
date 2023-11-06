package com.example.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.example.jsr303.LoginOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户实体类")
public class User implements Serializable {
    private static final long serialVersionUID = 1528247366805849494L;

    @Min(value = 0,message = "格式错误")
    @ApiModelProperty(value = "用户ID")
    @TableId(type = IdType.AUTO)
    private Long userId;

    @Pattern(
            regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$",
            message = "手机号不符合规范")
    @ApiModelProperty(value = "用户手机号",position = 1)
    private String phoneNum;

    @Length(max = 30, message = "用户名过长")
    @NotBlank(message = "用户名不能为空", groups = {LoginOperation.class})
    @ApiModelProperty(value = "用户名",position = 2)
    private String username;

    @NotBlank(message = "密码不能为空", groups = {LoginOperation.class})
    @ApiModelProperty(value = "密码",position = 3)
    private String password;

    @ApiModelProperty(value = "用户注册时间",hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime registerTime;

    @Pattern(
            regexp = "^([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z][a-zA-Z](([DF]((?![IO])[a-zA-Z0-9](?![IO]))[0-9]{4})|([0-9]{5}[DF]))|[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z][A-Z][A-Z0-9]{4}[A-Z0-9挂学警港澳])$",
            message = "车牌号格式不正确"
    )
    @ApiModelProperty(value = "用户车辆的车牌号",position = 5)
    private String carNum;

    @ApiModelProperty(value = "逻辑删除状态",hidden = true)
    @Max(value = 1, message = "格式错误")
    @Min(value = 0, message = "格式错误")
    private Integer delFlag;

    @ApiModelProperty(value = "乐观锁版本号",hidden = true)
    @Version
    private Integer version;
}
