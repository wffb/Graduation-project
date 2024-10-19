package org.example.bsproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.common.util.JwtUtil;
import org.example.bsproject.common.util.RedisUtil;
import org.example.bsproject.dto.Param.AdminUserInfoParam;
import org.example.bsproject.dto.Param.LoginParam;
import org.example.bsproject.dto.User;
import org.example.bsproject.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    JwtUtil jwtUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/updateUserInfo")
    @JsonView(User.UserDetailView.class)
    public CommonResult<Object> updateUserInfo(@RequestBody AdminUserInfoParam param, HttpServletRequest httpServletRequest){
        if(param==null||param.isEmpty())
            return  CommonResult.failed("提交修改信息不能为空");

        String token = httpServletRequest.getHeader(tokenHeader);
        String username = jwtUtil.getUsernameFromToken(token);

        return adminService.updateUserInfo(param,username);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/adminRegister")
    @JsonView(User.UserDetailView.class)
    public CommonResult<LoginParam> adminRegister(@RequestBody LoginParam param){
        if(param==null||param.isEmpty())
            return  CommonResult.failed("注册信息不能为空");

        return adminService.adminRegister(param);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/deleteUser")
    @JsonView(User.UserDetailView.class)
    public CommonResult<String> deleteUser(@RequestBody String username){
        if(username==null)
            return  CommonResult.failed("所要删除用户名不能为空");

        return  adminService.deleteUser(username);
    }
}
