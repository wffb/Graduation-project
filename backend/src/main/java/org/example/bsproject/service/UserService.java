package org.example.bsproject.service;

import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.Param.LoginParam;
import org.example.bsproject.dto.Param.UserInfoParam;
import org.example.bsproject.dto.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
public interface UserService extends IService<User> {

    public boolean userExist(String name);

    public CommonResult<UserInfoParam> updateUserInfo(UserInfoParam param);

    public Map<String,Object> login(LoginParam param);
    //登出
    public boolean logout();

    public CommonResult<LoginParam> userRegister(LoginParam param);

    public boolean isAdmin(String username);

    public User getUserByUsername(String username);

    public void refreshUserInRedis(String username);

    public void updateUserFee(String username, Integer fee);
}
