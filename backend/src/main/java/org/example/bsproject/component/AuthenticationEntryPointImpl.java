package org.example.bsproject.component;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.common.util.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

////登陆失败处理器
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    @JsonView(CommonResult.CommonResultView.class)
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        //待写入响应
        CommonResult<Object>commonResult= CommonResult.failed("登录验证失败，请重新确登录");
        //转为字符串（json）类
        String json = JSON.toJSONString(commonResult);
        //写入HTTP流
        WebUtils.renderString(response,json);
    }
}
