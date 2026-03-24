CREATE TABLE `prod_comm` (
  `comm_id` bigint NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `prod_id` bigint NOT NULL COMMENT '商品ID',
  `order_item_id` bigint NOT NULL COMMENT '订单项ID',
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `score` tinyint(1) DEFAULT '5' COMMENT '评分，1-5分',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评价内容',
  `pics` json DEFAULT NULL COMMENT '评价图片，JSON格式',
  `is_anonymous` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否匿名(1:是 0:否)',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态(0:未审核 1:已审核 -1:审核失败)',
  `rec_time` datetime DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`comm_id`) USING BTREE,
  KEY `idx_prod_id` (`prod_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品评价';