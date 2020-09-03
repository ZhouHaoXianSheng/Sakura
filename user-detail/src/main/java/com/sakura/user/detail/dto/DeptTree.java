package com.sakura.user.detail.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Mr.Zhou
 * 部门树
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends TreeNode {

	private String name;

}
