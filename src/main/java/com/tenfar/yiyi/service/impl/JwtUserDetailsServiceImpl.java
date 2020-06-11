package com.tenfar.yiyi.service.impl;

import com.tenfar.yiyi.mapper.UsersMapper;
import com.tenfar.yiyi.model.Users;
import com.tenfar.yiyi.service.JwtUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author tenfar
 */
@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    public UserDetails getByUsername(String username) throws UsernameNotFoundException {

        Users user = usersMapper.findOneByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                new ArrayList<>());
    }
}
