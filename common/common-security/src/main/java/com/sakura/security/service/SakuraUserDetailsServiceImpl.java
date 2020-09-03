package com.sakura.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.sakura.common.core.util.R;
import com.sakura.common.core.constant.CacheConstants;
import com.sakura.common.core.constant.CommonConstants;
import com.sakura.common.core.constant.SecurityConstants;
import com.sakura.user.detail.dto.UserInfo;
import com.sakura.user.detail.entity.SysUser;
import com.sakura.user.detail.feign.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.sakura.common.core.constant.SecurityConstants.BCRYPT;
import static com.sakura.common.core.constant.SecurityConstants.FROM_IN;

/**
 * 用户详细信息
 *
 * @author Mr.zhou
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SakuraUserDetailsServiceImpl implements UserDetailsService {

	private final RemoteUserService remoteUserService;

	private final CacheManager cacheManager;

	/**
	 * 用户密码登录
	 * @param username 用户名
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(username) != null) {
			return (SakuraUser) cache.get(username).get();
		}

		R<UserInfo> result = remoteUserService.info(username, FROM_IN);
		UserDetails userDetails = getUserDetails(result);
		if (cache != null) {
			cache.put(username, userDetails);
		}
		return userDetails;
	}

	/**
	 * 构建userdetails
	 * @param result 用户信息
	 * @return
	 */
	private UserDetails getUserDetails(R<UserInfo> result) {
		if (result == null || result.getData() == null) {
			throw new UsernameNotFoundException("用户不存在");
		}

		UserInfo info = result.getData();
		Set<String> dbAuthsSet = new HashSet<>();
		if (ArrayUtil.isNotEmpty(info.getRoles())) {
			// 获取角色
			Arrays.stream(info.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
			// 获取资源
			dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));

		}
		Collection<? extends GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(dbAuthsSet.toArray(new String[0]));
		SysUser user = info.getSysUser();

		// 构造security用户
		return new SakuraUser(user.getUserId(), user.getDeptId(), user.getUsername(),
				BCRYPT + user.getPassword(),
				StrUtil.equals(user.getLockFlag(), CommonConstants.STATUS_NORMAL), true, true, true, authorities);
	}

}
