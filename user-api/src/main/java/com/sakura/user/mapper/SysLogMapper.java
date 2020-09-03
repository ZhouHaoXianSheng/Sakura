

package com.sakura.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.user.detail.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 日志表 Mapper 接口
 * </p>
 *
 * @author Mr.Zhou
 * @since 2019/2/1
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

}
