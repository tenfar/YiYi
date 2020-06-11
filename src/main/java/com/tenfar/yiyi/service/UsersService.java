package com.tenfar.yiyi.service;

import com.tenfar.yiyi.model.Users;

/**
 * @author tenfar
 */
public interface UsersService {

    /**
     * 依据用户ID找出用户信息
     *
     * @param userId 用户ID
     * @return 返回用户Model
     */
    Users selectById(String userId);
}
