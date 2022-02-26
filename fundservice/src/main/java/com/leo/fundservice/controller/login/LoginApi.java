package com.leo.fundservice.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/fund")
public class LoginApi {

    @PostMapping("/login")
    public String loginFundManage(){
        log.info("登陆接口");
        return "LOGIN_SUCCESS";
    }

    @GetMapping("/getTest")
    public String getTest(){
        log.info("get接口");
        return "GET_SUCCESS";
    }
}
