package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Mr.zhou
 * @date 2019/2/1 部门树
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends TreeNode {

	private String name;

}
