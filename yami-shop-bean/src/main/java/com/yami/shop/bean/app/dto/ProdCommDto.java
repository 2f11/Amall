package com.yami.shop.bean.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 商品评价DTO
 */
@Data
@Schema(description = "商品评价DTO")
public class ProdCommDto {

    @Schema(description = "评价ID")
    private Long commId;

    @Schema(description = "商品ID")
    private Long prodId;

    @Schema(description = "得分")
    private Integer score;

    @Schema(description = "评价内容")
    private String content;

    @Schema(description = "图片")
    private String pics;

    @Schema(description = "是否匿名")
    private Integer isAnonymous;

    @Schema(description = "记录时间")
    private Date recTime;

    @Schema(description = "用户昵称")
    private String nickName;

    @Schema(description = "用户头像")
    private String pic;
}
