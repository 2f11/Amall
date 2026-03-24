package com.yami.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yami.shop.bean.app.dto.ProdCommDto;
import com.yami.shop.bean.model.ProdComm;
import com.yami.shop.common.util.PageAdapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProdCommMapper extends BaseMapper<ProdComm> {

    /**
     * 分页查询商品评价列表（带用户信息）
     */
    List<ProdCommDto> getProdCommList(@Param("page") PageAdapter page, @Param("prodId") Long prodId);

    /**
     * 统计商品评价总数
     */
    Long countProdComm(@Param("prodId") Long prodId);
}
