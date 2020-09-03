package com.example.demo.config;

import com.example.demo.handler.FormAuthenticationFailureHandler;
import com.example.demo.securityService.UserDetailsServiceImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @author Mr.zhou
 * @date 2019/2/1 认证相关配置
 */
@Primary
@Order(90)
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http.formLogin()
                //前往登陆页面
                .loginPage("/token/login")
                //submit前往的页面
                .loginProcessingUrl("/token/form")
                //自定义表单登陆失败处理器
                .failureHandler(authenticationFailureHandler())
                .and()
                .authorizeRequests().antMatchers("/token/**", "/actuator/**", "/test/**", "/user/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 此方法一般用于过滤资源，让请求不通过过滤器
     * 而HttpSecurity的方法则是仍然让请求通过过滤器，只不过是匿名方式
     * 不通过过滤器会产生一个问题，getContext获取不到用户
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**");
    }

    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    /**
     * 自定义表单登陆失败处理器
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new FormAuthenticationFailureHandler();
    }

    /**
     * https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-storage-updated
     * Encoded password does not look like BCrypt
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	/*public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/


}
