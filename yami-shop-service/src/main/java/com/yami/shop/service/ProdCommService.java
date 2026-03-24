package com.yami.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yami.shop.bean.app.dto.ProdCommDto;
import com.yami.shop.bean.model.ProdComm;

public interface ProdCommService extends IService<ProdComm> {

    /**
     * 获取商品评价列表（带用户信息）
     */
    IPage<ProdCommDto> getProdCommDtoPageByProdId(Page<ProdCommDto> page, Long prodId);
}
