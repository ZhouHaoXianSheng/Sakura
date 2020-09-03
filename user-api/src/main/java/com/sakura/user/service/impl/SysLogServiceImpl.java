

package com.sakura.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakura.user.detail.entity.SysLog;
import com.sakura.user.mapper.SysLogMapper;
import com.sakura.user.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author Mr.Zhou
 * @since 2019/2/1
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

}
