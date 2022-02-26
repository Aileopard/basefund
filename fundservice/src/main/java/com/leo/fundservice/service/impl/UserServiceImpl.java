package com.leo.fundservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo.fundservice.model.FundUser;
import com.leo.fundservice.mapper.UserMapper;
import com.leo.fundservice.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, FundUser> implements UserService {

    @Override
    public FundUser selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<FundUser>().eq("username", username));
    }
}
