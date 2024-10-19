package org.example.bsproject.service.Imp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.common.util.JwtUtil;
import org.example.bsproject.common.util.RedisUtil;
import org.example.bsproject.dto.Detail.LoginUser;
import org.example.bsproject.dto.Param.LoginParam;
import org.example.bsproject.dto.Param.UserInfoParam;
import org.example.bsproject.dto.User;
import org.example.bsproject.mapper.UserMapper;
import org.example.bsproject.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    JwtUtil jwtUtil;
    @Value("${redis_time.login}")
    Integer loginTime;
    @Value("${redis_time.used}")
    Integer usedTime;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public boolean userExist(String name) {
        if(name==null) return false;
        //缓存查找
        if(redisUtil.hasKey("login:"+name)||redisUtil.hasKey("used_user:"+name))
            return true;
        //数据库查找
        QueryWrapper<User>queryWrapper = new QueryWrapper<User>().eq("username",name);
        return !Objects.isNull(userMapper.selectOne(queryWrapper));
    }

    /**
     * 普通用户更新自身信息
     *
     * @param param
     * @return
     */
    @Override
    public CommonResult<UserInfoParam> updateUserInfo(UserInfoParam param) {



        if(!userExist(param.getUsername()))
//        if(false)
            return CommonResult.failed("用户不存在");
        if(isAdmin(param.getUsername()))
            return  CommonResult.failed("权限不足，无法修改管理员账号");

        String username = param.getUsername();
        User user =User.getParam(param);

        //更新数据库
        QueryWrapper<User> wrapper =new QueryWrapper<User>().eq("username",username);
        userMapper.update(user,wrapper);
        //更新缓存
        refreshUserInRedis(param.getUsername());

        return CommonResult.success("用户信息更新成功",param);

    }

    /**
     * 所有类型用户登录
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> login(LoginParam param) {
        //封装用户信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(param.getUsername(),param.getPassword());
        //调用进行检查
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        //authenticate存入redis
        redisUtil.set("login:"+loginUser.getUser().getUsername(),loginUser,loginTime);

        String jwt = jwtUtil.generateToken(loginUser,loginUser.getUser().getUserType());


        //把token响应给前端
        HashMap<String,Object> map = new HashMap<>();
        map.put("token",jwt);
        map.put("isAdmin",loginUser.getUser().getUserType().toString());
        map.put("user",loginUser.getUser());


        return map;
    }


    @Override
    public boolean logout() {
        //获取上下文
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String username = loginUser.getUser().getUsername();
        //清空redis
        redisUtil.del("login:"+username);

        return true;
    }

    /**
     * 一般用户注册
     * @param param
     * @return
     */
    @Override
    public CommonResult<LoginParam> userRegister(LoginParam param) {

        //判断重名
        if(userExist(param.getUsername()))
            return CommonResult.failed("当前用户名已存在");

        //存入用户
        User user = User.getParam(param,false);
        String pwd = user.getPassword();
        //密码加密存储
        user.setPassword(passwordEncoder.encode(pwd));
        userMapper.insert( user);

        return CommonResult.success("用户注册成功,请尽快完善个人信息",null);

    }


    @Override
    public User getUserByUsername(String username) {
        //缓存获取
        if(redisUtil.hasKey("login:"+username)){
           LoginUser loginUser = (LoginUser)redisUtil.get("login:"+username);
           return loginUser.getUser();
        }
        else if(redisUtil.hasKey("used_user:"+username)){
            return (User)redisUtil.get("used_user:"+username);
        }
        //数据库搜索
        else{
            User user=userMapper.selectByUsernameUser(username);
            redisUtil.set("used_user:"+username,user,5);
            return user;
        }
    }

    @Override
    public boolean isAdmin(String username) {
       return getUserByUsername(username).getUserType();
    }

    //查看最新数据更新缓存用户信息
    public void refreshUserInRedis(String username){

        Wrapper<User>wrapper=new QueryWrapper<User>().eq("username",username);
        User user = userMapper.selectOne(wrapper);
        //更新消息
        if(!Objects.isNull(user)){
            //保留旧的 登陆时间-lastlogin
            LoginUser loginUser = (LoginUser) redisUtil.get("login:"+username);
            if(!Objects.isNull(loginUser)){
                loginUser.setUser(user);
                redisUtil.set("login:"+username,loginUser);
            }
            if(redisUtil.hasKey("used_user:"+username))
                redisUtil.set("used_user:"+username,user,usedTime);
        }
        //同步删除
        else {
            redisUtil.del("login:"+username);
            redisUtil.del("used_user:"+username);
        }
    }

    @Override
    public void updateUserFee(String username, Integer fee) {
        //无变化
        if(fee==0) return;
        //更新应缴费用
        User user = getUserByUsername(username);
        user.setFee(user.getFee()+fee);

        QueryWrapper<User>wrapper=new QueryWrapper<User>().eq("username",username);
        userMapper.update(user,wrapper);

        refreshUserInRedis(username);
    }


}
