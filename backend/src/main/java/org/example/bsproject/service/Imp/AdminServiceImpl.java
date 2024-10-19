package org.example.bsproject.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.common.util.RedisUtil;
import org.example.bsproject.dto.Detail.LoginUser;
import org.example.bsproject.dto.Param.AdminUserInfoParam;
import org.example.bsproject.dto.Param.LoginParam;
import org.example.bsproject.dto.User;
import org.example.bsproject.mapper.UserMapper;
import org.example.bsproject.service.AdminService;
import org.example.bsproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userService;

    @Override
    public CommonResult<Object> updateUserInfo(AdminUserInfoParam param,String username) {


        if(!userService.userExist(param.getUsername()))
            return CommonResult.failed("用户不存在");

        //管理员不能修改其他管理员信息
        if(userService.isAdmin(param.getUsername())&& !param.getUsername().equals(username))
            return  CommonResult.failed("权限不足，无法修改管理员账号");

        User user =User.getParam(param);
        QueryWrapper<User> wrapper =new QueryWrapper<User>().eq("username",param.getUsername());

        //密码加密
        if(!Objects.isNull(param.getPassword())){
            String pwd = passwordEncoder.encode(param.getPassword());
            param.setPassword(pwd);
        }

        if(userMapper.update(user,wrapper)!=0)
            return CommonResult.success("用户信息更新成功");
        else
            return CommonResult.failed("用户信息更新失败");
    }

    @Override
    public CommonResult<LoginParam> adminRegister(LoginParam param) {

        if(userService.userExist(param.getUsername()))
            return CommonResult.failed("当前用户名已存在");

        //存入用户
        User user = User.getParam(param,true);
        String pwd = user.getPassword();
        //密码加密存储
        user.setPassword(passwordEncoder.encode(pwd));
        userMapper.insert(user);

        return CommonResult.success("用户注册成功",param);
    }

    @Override
    public CommonResult<String> deleteUser(String username) {


        if(!userService.userExist(username))
            return CommonResult.failed("所要删除的用户不存在");

        if(userService.isAdmin(username))
            return CommonResult.failed("无法删除管理员信息");

        QueryWrapper<User>wrapper =new QueryWrapper<User>().eq("username",username);
        userMapper.delete(wrapper);

        //缓存同步
        redisUtil.del("login:"+username);
        redisUtil.del("used_user:"+username);

        return  CommonResult.success("用户删除成功");
    }






}
