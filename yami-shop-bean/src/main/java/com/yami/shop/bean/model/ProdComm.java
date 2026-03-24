package com.yami.shop.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品评价
 */
@Data
@TableName("prod_comm")
public class ProdComm implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 评价ID
     */
    @TableId(type = IdType.AUTO)
    private Long commId;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 订单项ID
     */
    private Long orderItemId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 评分，1-5分
     */
    private Integer score;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价图片，JSON格式
     */
    private String pics;

    /**
     * 是否匿名(1:是 0:否)
     */
    private Integer isAnonymous;

    /**
     * 状态(0:未审核 1:已审核 -1:审核失败)
     */
    private Integer status;

    /**
     * 记录时间
     */
    private Date recTime;

}
