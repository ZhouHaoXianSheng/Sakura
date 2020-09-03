

package com.sakura.user.detail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 部门关系表
 * </p>
 *
 * @author Mr.Zhou
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptRelation extends Model<SysDeptRelation> {

	private static final long serialVersionUID = 1L;

	/**
	 * 祖先节点
	 */
	@ApiModelProperty(value = "祖先节点")
	private Integer ancestor;

	/**
	 * 后代节点
	 */
	@ApiModelProperty(value = "后代节点")
	private Integer descendant;

}
