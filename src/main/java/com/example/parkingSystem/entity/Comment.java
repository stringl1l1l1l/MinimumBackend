package com.example.parkingSystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("停车场评论实体类")
public class Comment {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("评论ID")
    private Long commentId;

    @ApiModelProperty(value = "拥有该条评论的停车场ID",notes = "外键",position = 1)
    private Long parkingLotId;

    @ApiModelProperty(value = "评论内容",position = 2)
    private String content;

    @ApiModelProperty(value = "评分,5分制",position = 3)
    private Integer score;

    @ApiModelProperty(value = "逻辑删除标识",hidden = true)
    private Integer delFlag;

    @Version
    @ApiModelProperty(value = "乐观锁版本",hidden = true)
    private Integer version;
}
