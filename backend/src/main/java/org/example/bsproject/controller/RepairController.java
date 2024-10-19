package org.example.bsproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.common.util.JwtUtil;
import org.example.bsproject.common.util.RedisUtil;
import org.example.bsproject.dto.CommunityInfo;
import org.example.bsproject.dto.Detail.LoginUser;
import org.example.bsproject.dto.Param.PublicFacilitiesParam;
import org.example.bsproject.dto.Param.RepairInfoParam;
import org.example.bsproject.dto.Param.UserRepairInfoParam;
import org.example.bsproject.dto.PublicFacilities;
import org.example.bsproject.dto.RepairInfo;
import org.example.bsproject.service.RepairInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class RepairController {

    @Autowired
    RepairInfoService repairInfoService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    JwtUtil jwtUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;


    //用户查看-只显示自己的信息
    @PreAuthorize("hasAnyAuthority('user')")
    @GetMapping("/user/getAllRepair")
    @JsonView(RepairInfo.RepairInfoDetailView.class)
    public CommonResult<List<RepairInfo>> usGetAllInfoInList( HttpServletRequest httpRequest){
        //自动从token获取用户名
        String token=httpRequest.getHeader(tokenHeader);
        String name=jwtUtil.getUsernameFromToken(token);

        return repairInfoService.listByUsername(name);
    }

    //根据身份自动化查询
    @PreAuthorize("hasAnyAuthority('user')")
    @GetMapping("/getRepair")
    public CommonResult<List<RepairInfo>> getInfoInList( HttpServletRequest httpRequest){
        //自动从token获取用户名
        String token=httpRequest.getHeader(tokenHeader);
        String name=jwtUtil.getUsernameFromToken(token);

        //从redis获取身份
        String redisKey = "login:" + name;
        LoginUser loginUser = (LoginUser) redisUtil.get(redisKey);
        Boolean isAdmin = loginUser.getUser().getUserType();

        if(isAdmin)
            return CommonResult.success(repairInfoService.list());
        else
            return repairInfoService.listByUsername(name);
    }

    //管理员查看-显示全部
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/admin/getAllRepair")
    @JsonView(RepairInfo.RepairInfoDetailView.class)
    public CommonResult<List<RepairInfo>> adGetAllInfoInList(){
        return CommonResult.success(repairInfoService.list());
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/admin/getRepairByUsername/{name}")
    @JsonView(RepairInfo.RepairInfoDetailView.class)
    public CommonResult<List<RepairInfo>> adGetInfoInListByUsername(@PathVariable("name") String name){
        return repairInfoService.listByUsername(name);
    }


    //用户提交
    @PreAuthorize("hasAnyAuthority('user')")
    @PostMapping("/user/addRepair")
    @JsonView(RepairInfo.RepairInfoSimpleView.class)
    public CommonResult<RepairInfoParam> addInfo(@RequestBody UserRepairInfoParam content, HttpServletRequest httpRequest){
        //自动从token获取用户名
        String token=httpRequest.getHeader(tokenHeader);
        String name=jwtUtil.getUsernameFromToken(token);

        RepairInfoParam param = new RepairInfoParam();
        param.setContent(content.getContent());
        param.setStatus(false);

        return repairInfoService.saveByParam(param,name);
    }
    //用户修改
    @PreAuthorize("hasAnyAuthority('user')")
    @PostMapping("/user/updateRepair")
    @JsonView(RepairInfo.RepairInfoDetailView.class)
    public CommonResult<RepairInfo> updateInfo(@RequestBody UserRepairInfoParam content, HttpServletRequest httpRequest){
        //自动从token获取用户名
        String token=httpRequest.getHeader(tokenHeader);
        String name=jwtUtil.getUsernameFromToken(token);

        return repairInfoService.userUpdateByParam(content,name);
    }

    //管理员更新
    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("/admin/deleteRepair/{id}")
    @JsonView(RepairInfo.RepairInfoDetailView.class)
    public CommonResult<RepairInfo> deleteInfo(@PathVariable("id") Long id){
        if(repairInfoService.removeById(id))
            return CommonResult.success("删除成功");

        else
            return  CommonResult.failed("删除失败");
    }

    //管理员删除
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/updateRepair")
    @JsonView(RepairInfo.RepairInfoDetailView.class)
    public CommonResult<RepairInfo> updateInfo(@RequestBody RepairInfoParam param){
        return  repairInfoService.updateByParam(param);
    }
}
