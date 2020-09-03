package com.sakura.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sakura.common.core.util.R;
import com.sakura.user.detail.entity.SysOauthClientDetails;
import com.sakura.user.service.SysOauthClientDetailsService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Mr.Zhou
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Api(value = "client", tags = "客户端管理模块")
public class OauthClientDetailsController {

	private final SysOauthClientDetailsService sysOauthClientDetailsService;

	/**
	 * 通过ID查询
	 * @param id clientId
	 * @return SysOauthClientDetails
	 */
	@GetMapping("/{id}")
	public R getById(@PathVariable String id) {
		return R.ok(sysOauthClientDetailsService.getById(id));
	}

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param sysOauthClientDetails 系统终端
	 * @return
	 */
	@GetMapping("/page")
	public R getOauthClientDetailsPage(Page page, SysOauthClientDetails sysOauthClientDetails) {
		return R.ok(sysOauthClientDetailsService.page(page, Wrappers.query(sysOauthClientDetails)));
	}

	/**
	 * 添加
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_client_add')")
	public R add(@Valid @RequestBody SysOauthClientDetails sysOauthClientDetails) {
		return R.ok(sysOauthClientDetailsService.save(sysOauthClientDetails));
	}

	/**
	 * 删除
	 * @param id ID
	 * @return success/false
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_client_del')")
	public R removeById(@PathVariable String id) {
		return R.ok(sysOauthClientDetailsService.removeClientDetailsById(id));
	}

	/**
	 * 编辑
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_client_edit')")
	public R update(@Valid @RequestBody SysOauthClientDetails sysOauthClientDetails) {
		return R.ok(sysOauthClientDetailsService.updateClientDetailsById(sysOauthClientDetails));
	}

}
