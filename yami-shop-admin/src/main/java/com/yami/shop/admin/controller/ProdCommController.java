package com.yami.shop.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yami.shop.bean.model.ProdComm;
import com.yami.shop.common.response.ServerResponseEntity;
import com.yami.shop.service.ProdCommService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prod/prodComm")
@Tag(name = "商品评价管理接口")
@AllArgsConstructor
public class ProdCommController {

    private final ProdCommService prodCommService;

    @GetMapping("/page")
    @Operation(summary = "分页查询商品评价")
    public ServerResponseEntity<IPage<ProdComm>> getProdCommPage(Page<ProdComm> page, ProdComm prodComm) {
        IPage<ProdComm> prodCommPage = prodCommService.page(page, new LambdaQueryWrapper<ProdComm>()
                .eq(prodComm.getStatus() != null, ProdComm::getStatus, prodComm.getStatus())
                .orderByDesc(ProdComm::getRecTime));
        return ServerResponseEntity.success(prodCommPage);
    }

    @PutMapping
    @Operation(summary = "审核商品评价")
    public ServerResponseEntity<Void> updateProdComm(@RequestBody ProdComm prodComm) {
        prodCommService.updateById(prodComm);
        return ServerResponseEntity.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除商品评价")
    public ServerResponseEntity<Void> deleteProdComm(@PathVariable Long id) {
        prodCommService.removeById(id);
        return ServerResponseEntity.success();
    }
}
