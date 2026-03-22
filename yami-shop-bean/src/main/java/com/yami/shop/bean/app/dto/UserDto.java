/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.yami.shop.bean.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
 * @author lanhai
 */
@Data
public class UserDto {

	@Schema(description = "用户id")
	private String userId;

	@Schema(description = "用户昵称")
	private String nickName;

	@Schema(description = "用户头像")
	private String pic;

	@Schema(description = "用户状态：0禁用 1正常" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer status;
	@Schema(description = "token" ,requiredMode = Schema.RequiredMode.REQUIRED)
	private String token;

}
