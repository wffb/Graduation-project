package org.example.bsproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.common.util.JwtUtil;
import org.example.bsproject.common.util.RedisUtil;
import org.example.bsproject.dto.FeeInfo;
import org.example.bsproject.dto.Param.FeeInfoParam;
import org.example.bsproject.service.FeeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class FeeController {

    @Autowired
    FeeInfoService feeInfoService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    JwtUtil jwtUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;


    @PreAuthorize("hasAnyAuthority('user')")
    @GetMapping("/listFeeInfo")
    @JsonView(FeeInfo.FeeInfoDetailView.class)
    public CommonResult<List<FeeInfo>> listAll(HttpServletRequest request){
        //获取身份
        String token = request.getHeader(tokenHeader);
        Boolean isAdmin = jwtUtil.getIsAdminFromToken(token);
        if(isAdmin)
            return CommonResult.success(feeInfoService.list());
        else {
            String username = jwtUtil.getUsernameFromToken(token);
            if(username==null)
                return CommonResult.failed("查询用户名不能为空");
            return feeInfoService.listByName(username);
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/listFeeInfoByName/{name}")
    @JsonView(FeeInfo.FeeInfoDetailView.class)
    public CommonResult<List<FeeInfo>> listByName(@PathVariable("name")String username){
        if(username==null)
            return CommonResult.failed("查询用户名不能为空");
        return feeInfoService.listByName(username);
    }


    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/saveFeeInfo")
    @JsonView(FeeInfo.FeeInfoDetailView.class)
    public CommonResult<FeeInfoParam> saveFeeInfo(@RequestBody FeeInfoParam param ){
        //信息判空
        if(param==null||param.checkEmpty()||param.getUserName()==null)
            return CommonResult.failed("信息id不能为空");

        return  feeInfoService.saveByParam(param);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("/admin/deleteFeeInfo/{id}")
    @JsonView(FeeInfo.FeeInfoDetailView.class)
    public CommonResult<FeeInfo> removeById(@PathVariable("id") String id){
        if(id==null)
            return CommonResult.failed("删除缴费信息id不能为空");

        if(feeInfoService.removeById(id))
            return CommonResult.success("删除成功");
        else
            return CommonResult.failed("删除失败");
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/updateFeeInfo")
    @JsonView(FeeInfo.FeeInfoDetailView.class)
    public CommonResult<FeeInfo> updateByParam(@RequestBody FeeInfoParam param){
        //信息判空
        if(param==null||param.checkEmpty()||param.getId()==null)
            return CommonResult.failed("缴费信息不能为空");

        return feeInfoService.updateByParam(param);
    }

}
