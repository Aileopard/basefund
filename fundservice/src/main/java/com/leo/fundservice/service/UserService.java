package com.leo.fundservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo.fundservice.model.FundUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface UserService extends IService<FundUser> {

    // 从数据库中取出用户信息
    FundUser selectByUsername(String username);
}
