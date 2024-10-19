package org.example.bsproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.CommunityInfo;
import org.example.bsproject.dto.Param.CommunityInfoParam;
import org.example.bsproject.service.CommunityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommunityController {
    @Autowired
    CommunityInfoService communityInfoService;


    //用户-管理员查看
    @GetMapping("/getAllCommunity")
    @JsonView(CommunityInfo.CommunityDetailView.class)
    public CommonResult<List<CommunityInfo>>  adGetAllInfoInList(){
        return CommonResult.success(communityInfoService.list());
    }


    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/addCommunity")
    @JsonView(CommunityInfo.CommunityDetailView.class)
    public CommonResult<CommunityInfoParam> addInfo(@RequestBody CommunityInfoParam communityInfo){
        return  communityInfoService.saveByParam(communityInfo);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/deleteCommunity")
    @JsonView(CommunityInfo.CommunityDetailView.class)
   public CommonResult<CommunityInfo> deleteInfo(@RequestBody String name){
        return  communityInfoService.removeByParam(name);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/updateCommunity")
    @JsonView(CommunityInfo.CommunityDetailView.class)
    public CommonResult<CommunityInfo> updateInfo(@RequestBody CommunityInfoParam communityInfo){
        return  communityInfoService.updateyParam(communityInfo);
    }


}
