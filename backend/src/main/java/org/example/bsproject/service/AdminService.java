package org.example.bsproject.service;

import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.Param.AdminUserInfoParam;
import org.example.bsproject.dto.Param.LoginParam;
import org.example.bsproject.dto.User;

public interface AdminService {

    public CommonResult<Object> updateUserInfo(AdminUserInfoParam param,String username);

    public CommonResult<LoginParam> adminRegister(LoginParam param);

    public CommonResult<String> deleteUser(String username);

}
