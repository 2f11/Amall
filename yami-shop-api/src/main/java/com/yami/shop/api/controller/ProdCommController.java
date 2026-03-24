package com.yami.shop.api.controller;

import com.yami.shop.bean.enums.OrderStatus;
import com.yami.shop.bean.model.Order;
import com.yami.shop.bean.model.OrderItem;
import com.yami.shop.bean.model.ProdComm;
import com.yami.shop.common.response.ServerResponseEntity;
import com.yami.shop.security.api.util.SecurityUtils;
import com.yami.shop.service.OrderItemService;
import com.yami.shop.service.OrderService;
import com.yami.shop.service.ProdCommService;
import com.yami.shop.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/p/prodComm")
@Tag(name = "商品评价接口")
@AllArgsConstructor
public class ProdCommController {

    private final ProdCommService prodCommService;

    private final OrderItemService orderItemService;

    private final OrderService orderService;

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "提交商品评价")
    public ServerResponseEntity<Void> addProdComm(@RequestBody ProdComm prodComm) {
        if (prodComm.getProdId() == null || prodComm.getOrderItemId() == null) {
            return ServerResponseEntity.showFailMsg("评价信息不完整");
        }

        // 幂等性校验：检查该订单项是否已经评价过
        OrderItem orderItemObj = orderItemService.getById(prodComm.getOrderItemId());
        if (orderItemObj == null || (orderItemObj.getCommSts() != null && orderItemObj.getCommSts() == 1)) {
            return ServerResponseEntity.showFailMsg("该商品已评价，请勿重复提交");
        }

        String userId = SecurityUtils.getUser().getUserId();
        prodComm.setUserId(userId);
        prodComm.setRecTime(new Date());
        prodComm.setStatus(1); // 设置为1，发布后直接可见（优化体验）
        prodCommService.save(prodComm);

        // 更新订单项的评价状态，并清理缓存
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(prodComm.getOrderItemId());
        orderItem.setCommSts(1);
        orderItemService.updateOrderItemAndClearCache(orderItem, orderItemObj.getOrderNumber());

        // 额外清理一次订单缓存，确保详情页数据最新
        productService.removeProductCacheByProdId(prodComm.getProdId());

        // 检查订单下所有项是否都已评价，如果是则更新订单状态
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderNumber(orderItemObj.getOrderNumber());
        boolean allComm = true;
        for (OrderItem item : orderItems) {
            if (item.getCommSts() == null || item.getCommSts() == 0) {
                allComm = false;
                break;
            }
        }

        if (allComm) {
            Order order = orderService.getOrderByOrderNumber(orderItemObj.getOrderNumber());
            if (order != null && order.getStatus() == OrderStatus.CONFIRM.value()) {
                Order updateOrder = new Order();
                updateOrder.setOrderId(order.getOrderId());
                updateOrder.setStatus(OrderStatus.SUCCESS.value());
                orderService.updateById(updateOrder);
            }
        }

        return ServerResponseEntity.success();
    }
}
