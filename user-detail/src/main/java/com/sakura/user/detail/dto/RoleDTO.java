package com.sakura.user.detail.dto;

import com.sakura.user.detail.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Mr.Zhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends SysRole {

	/**
	 * 角色部门Id
	 */
	private Integer roleDeptId;

	/**
	 * 部门名称
	 */
	private String deptName;

}
