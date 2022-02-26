package com.leo.fundservice.service.impl;

import com.leo.fundservice.model.FundUser;
import com.leo.fundservice.security.entity.SecurityUser;
import com.leo.fundservice.security.entity.User;
import com.leo.fundservice.service.PermissionService;
import com.leo.fundservice.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询数据
        FundUser user = userService.selectByUsername(username);
        //判断
        if(user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        User curUser = new User();
        BeanUtils.copyProperties(user,curUser);

        //根据用户查询用户权限列表
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(curUser);
        securityUser.setPermissionValueList(permissionValueList);
        return securityUser;
    }
}
