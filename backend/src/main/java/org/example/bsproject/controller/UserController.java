package org.example.bsproject.controller;

import cn.hutool.system.UserInfo;
import com.fasterxml.jackson.annotation.JsonView;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.Param.LoginParam;
import org.example.bsproject.dto.Param.UserInfoParam;
import org.example.bsproject.dto.User;
import org.example.bsproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @JsonView(User.UserSimpleView.class)
    public CommonResult<Map<String,Object>> login(@RequestBody LoginParam param){

        if(param==null|| param.isEmpty())
            return  CommonResult.failed("用户信息不能为空");

        return CommonResult.success("登陆成功",userService.login(param));
    }

    @PostMapping("/register")
    @JsonView(User.UserSimpleView.class)
    public   CommonResult<LoginParam>  register(@RequestBody LoginParam param){

        if(param==null|| param.isEmpty())
            return  CommonResult.failed("用户信息不能为空");

        return userService.userRegister(param);
    }

    @PreAuthorize("hasAnyAuthority('user')")
    @PostMapping("/updateUserInfo")
    public  CommonResult<UserInfoParam> updateUserInfo(@RequestBody UserInfoParam param){

        return userService.updateUserInfo(param);
    }

    @JsonView(User.UserSimpleView.class)
    @GetMapping("/getUserInfoByUsername/{username}")
    public CommonResult<Object> getUserInfoByName(@PathVariable("username") String username){

        User user= userService.getUserByUsername(username);
        if (user!=null)
            return CommonResult.success("用户信息获取成功",user);
        else
            return CommonResult.failed("用户信息获取失败");
    }
}
