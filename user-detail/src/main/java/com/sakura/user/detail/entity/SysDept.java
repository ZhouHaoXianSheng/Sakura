package com.sakura.user.detail.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * <p>
 * 部门管理
 * </p>
 * 继承Model<SysDept>是为了激活ActiveRecord模式
 * 也就是直接用实体类对数据库进行crud的操作
 * @author Mr.Zhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends Model<SysDept> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "dept_id", type = IdType.AUTO)
	@ApiModelProperty(value = "部门id")
	private Integer deptId;

	/**
	 * 部门名称
	 */
	@NotBlank(message = "部门名称不能为空")
	@ApiModelProperty(value = "部门名称")
	private String name;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序值")
	private Integer sort;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

	/**
	 * 父级部门id
	 */
	@ApiModelProperty(value = "父级部门id")
	private Integer parentId;

	/**
	 * 是否删除 -1：已删除 0：正常
	 * 此处为逻辑删除字段
	 */
	@TableLogic
	private String delFlag;

}
