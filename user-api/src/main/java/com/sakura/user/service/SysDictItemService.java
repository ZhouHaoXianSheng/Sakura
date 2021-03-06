
package com.sakura.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sakura.user.detail.entity.SysDictItem;
import com.sakura.common.core.util.R;

/**
 * 字典项
 *
 * @author Mr.Zhou
 * @date 2019/03/19
 */
public interface SysDictItemService extends IService<SysDictItem> {

	/**
	 * 删除字典项
	 * @param id 字典项ID
	 * @return
	 */
	R removeDictItem(Integer id);

	/**
	 * 更新字典项
	 * @param item 字典项
	 * @return
	 */
	R updateDictItem(SysDictItem item);

}
