package com.yami.shop.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yami.shop.bean.app.dto.ProdCommDto;
import com.yami.shop.bean.model.ProdComm;
import com.yami.shop.common.util.PageAdapter;
import com.yami.shop.dao.ProdCommMapper;
import com.yami.shop.service.ProdCommService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProdCommServiceImpl extends ServiceImpl<ProdCommMapper, ProdComm> implements ProdCommService {

    private final ProdCommMapper prodCommMapper;

    @Override
    public IPage<ProdCommDto> getProdCommDtoPageByProdId(Page<ProdCommDto> page, Long prodId) {
        page.setRecords(prodCommMapper.getProdCommList(new PageAdapter(page), prodId));
        page.setTotal(prodCommMapper.countProdComm(prodId));
        return page;
    }
}
