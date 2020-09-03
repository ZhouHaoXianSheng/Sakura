package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.example.demo.annotation.TestAnno;
import com.example.demo.common.R;
import com.example.demo.model.SysUser;
import com.example.demo.model.UserDTO;
import com.example.demo.service.SysUserService;
import com.example.demo.temp.Injetction;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Mr.zhou
 * @date 2019/2/1
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(value = "user", tags = "用户管理模块")
public class UserController {

	private final SysUserService userService;

	private final Injetction injetction;

	/**
	 * 获取当前用户全部信息
	 * @return 用户信息
	 */
//	@GetMapping(value = { "/info" })
//	public R info() {
//		String username = SecurityUtils.getUser().getUsername();
//		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
//		if (user == null) {
//			return R.failed("获取当前用户信息失败");
//		}
//		return R.ok(userService.getUserInfo(user));
//	}

	/**
	 * 获取指定用户全部信息
	 * @return 用户信息
	 */
//	@Inner
	@GetMapping("/info/{username}")
	public R info(@PathVariable String username) {
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
		if (user == null) {
			return R.failed(String.format("用户信息为空 %s", username));
		}
		return R.ok(userService.getUserInfo(user));
	}

	/**
	 * 通过ID查询用户信息
	 * @param id ID
	 * @return 用户信息
	 */
	@TestAnno("今天X是个好日子")
	@GetMapping("/{id}")
	public R user(@PathVariable Integer id) {
		return R.ok(userService.getUserVoById(id));
	}

	/**
	 * 根据用户名查询用户信息
	 * @param username 用户名
	 * @return
	 */
	@TestAnno("今天X是个好日子")
	@GetMapping("/details/{username}")
	public R user(@PathVariable String username) {
		SysUser condition = new SysUser();
		condition.setUsername(username);
		System.out.println(injetction.getName());
		return R.ok(userService.getOne(new QueryWrapper<>(condition)));
	}

	/**
	 * 删除用户信息
	 * @param id ID
	 * @return R
	 */
	@DeleteMapping("/{id}")
//	@PreAuthorize("@pms.hasPermission('sys_user_del')")
	public R userDel(@PathVariable Integer id) {
		SysUser sysUser = userService.getById(id);
		return R.ok(userService.removeUserById(sysUser));
	}

	/**
	 * 添加用户
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('sys_user_add')")
	public R user(@RequestBody UserDTO userDto) {
		return R.ok(userService.saveUser(userDto));
	}

	/**
	 * 更新用户信息
	 * @param userDto 用户信息
	 * @return R
	 */
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('sys_user_edit')")
	public R updateUser(@Valid @RequestBody UserDTO userDto) {
		return R.ok(userService.updateUser(userDto));
	}

	/**
	 * 分页查询用户
	 * @param page 参数集
	 * @param userDTO 查询参数列表
	 * @return 用户集合
	 */
	@GetMapping("/page")
	public R getUserPage(Page page, UserDTO userDTO) {
		return R.ok(userService.getUserWithRolePage(page, userDTO));
	}

	/**
	 * 修改个人信息
	 * @param userDto userDto
	 * @return success/false
	 */
	@PutMapping("/edit")
	public R updateUserInfo(@Valid @RequestBody UserDTO userDto) {
		return userService.updateUserInfo(userDto);
	}

	/**
	 * @param username 用户名称
	 * @return 上级部门用户列表
	 */
	@GetMapping("/ancestor/{username}")
	public R listAncestorUsers(@PathVariable String username) {
		return R.ok(userService.listAncestorUsersByUsername(username));
	}

}
