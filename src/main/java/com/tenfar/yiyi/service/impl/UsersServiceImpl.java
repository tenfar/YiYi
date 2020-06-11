package com.tenfar.yiyi.service.impl;

import com.tenfar.yiyi.mapper.UsersMapper;
import com.tenfar.yiyi.model.Users;
import com.tenfar.yiyi.service.UsersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author tenfar
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    public Users selectById(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }
}
