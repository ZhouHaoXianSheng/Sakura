package com.sakura.user.detail.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Mr.Zhou
 */
@Data
@ApiModel(value = "前端角色展示对象")
public class RoleVo {

	/**
	 * 角色id
	 */
	private Integer roleId;

	/**
	 * 菜单列表
	 */
	private String menuIds;

}
