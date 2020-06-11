package com.tenfar.yiyi.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author tenfar
 */
public interface JwtUserDetailsService {

    /**
     * 返回用户信息用于校验token
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserDetails getByUsername(String username);
}
