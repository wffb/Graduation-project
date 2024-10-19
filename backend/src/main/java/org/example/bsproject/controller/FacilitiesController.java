package org.example.bsproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.CommunityInfo;
import org.example.bsproject.dto.FacilitiesInfo;
import org.example.bsproject.dto.Param.CommunityInfoParam;
import org.example.bsproject.dto.Param.FacilitiesInfoParam;
import org.example.bsproject.dto.Param.PublicFacilitiesParam;
import org.example.bsproject.dto.PublicFacilities;
import org.example.bsproject.service.CommunityInfoService;
import org.example.bsproject.service.FacilitiesInfoService;
import org.example.bsproject.service.PublicFacilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FacilitiesController {

    //设施信息管理
    @Autowired
    PublicFacilitiesService publicFacilitiesService;
    @Autowired
    FacilitiesInfoService facilitiesInfoService;


    //管理员-用户查看
    @PreAuthorize("hasAnyAuthority('user')")
    @GetMapping("/getAllFacilities")
    @JsonView(PublicFacilities.PublicFacilitiesDetailView.class)
    public CommonResult<List<PublicFacilities>> adGetAllInfoInList(){
        return CommonResult.success(publicFacilitiesService.list());
    }

    //确认查询
    @PreAuthorize("hasAnyAuthority('user')")
    @GetMapping("/getOneFacility/{id}")
    @JsonView(PublicFacilities.PublicFacilitiesDetailView.class)
    public PublicFacilities adGetOneFacilty(@PathVariable("id") Long id){

        return publicFacilitiesService.getById(id);
    }


    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/addFacilities")
    @JsonView(PublicFacilities.PublicFacilitiesDetailView.class)
    public CommonResult<PublicFacilitiesParam> addInfo(@RequestBody PublicFacilitiesParam param){
        return  publicFacilitiesService.saveByParam(param);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("/admin/deleteFacilities/{name}")
    @JsonView(PublicFacilities.PublicFacilitiesDetailView.class)
    public CommonResult<PublicFacilities> deleteInfo(@PathVariable("name") String name){
        return  publicFacilitiesService.deleteByParam(name);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/updateFacilities")
    @JsonView(PublicFacilities.PublicFacilitiesDetailView.class)
    public CommonResult<PublicFacilities> updateInfo(@RequestBody PublicFacilitiesParam param){
        return  publicFacilitiesService.updateyParam(param);
    }

    //设施管理信息
    @PreAuthorize("hasAnyAuthority('user')")
    @GetMapping("/user/getFacInfoByFacId/{id}")
    @JsonView(FacilitiesInfo.FacilitiesInfoDetailView.class)
    public CommonResult<List<FacilitiesInfo>> getByFacId (@PathVariable("id") Long facId){
        if(facId==null)
            return CommonResult.failed("设施id不能为空");

         return facilitiesInfoService.getByFacId(facId);
    }

    //管理
    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("/admin/deleteFacInfoByFacId/{id}")
    @JsonView(FacilitiesInfo.FacilitiesInfoDetailView.class)
    public CommonResult<FacilitiesInfo> removeByFacId(@PathVariable("id") Long id){
        //信息判空
        if(id==null)
            return CommonResult.failed("设施id不能为空");

        return facilitiesInfoService.removeByFacId(id);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("/admin/deleteFacInfoById/{id}")
    @JsonView(FacilitiesInfo.FacilitiesInfoDetailView.class)
    public CommonResult<FacilitiesInfo> removeById(@PathVariable("id") Long id){
        //信息判空
        if(id==null)
            return CommonResult.failed("设施id不能为空");

        if(facilitiesInfoService.removeById(id))
            return CommonResult.success("删除成功");
        else
            return CommonResult.failed("删除失败");
    }


    @PostMapping("/admin/updateFacInfoByParam")
    @PreAuthorize("hasAnyAuthority('admin')")
    @JsonView(FacilitiesInfo.FacilitiesInfoDetailView.class)
    public CommonResult<FacilitiesInfo> updateByParam(@RequestBody FacilitiesInfoParam param){

        if(param==null||param.isEmpty())
            return CommonResult.failed("设施信息不能为空");

        return facilitiesInfoService.updateByParam(param);
    }

    @PostMapping("/admin/addFacInfoByParam")
    @PreAuthorize("hasAnyAuthority('admin')")
    @JsonView(CommonResult.CommonResultView.class)
    public CommonResult<FacilitiesInfoParam> saveByParam(@RequestBody FacilitiesInfoParam param){
        //信息判空
        if(param==null||param.isEmpty())
            return CommonResult.failed("设施信息不能为空");

        return facilitiesInfoService.saveByParam(param);
    }

//    @GetMapping("/admin/deleteFacInfoById")
//    @PreAuthorize("hasAnyAuthority('admin')")
//    @JsonView(FacilitiesInfo.FacilitiesInfoDetailView.class)
//    public CommonResult<List<FacilitiesInfo>> deleteById(Long id){
//        if(id==null)
//            return CommonResult.failed("维修记录id不能为空");
//
//        return facilitiesInfoService.deleteById(id);
//    }


}
