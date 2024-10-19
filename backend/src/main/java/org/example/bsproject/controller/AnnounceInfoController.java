package org.example.bsproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.component.WebSocket;
import org.example.bsproject.dto.AnnounceInfo;
import org.example.bsproject.dto.CommunityInfo;
import org.example.bsproject.dto.Param.AnnounceInfoParam;
import org.example.bsproject.dto.Param.CommunityInfoParam;
import org.example.bsproject.service.AnnounceInfoService;
import org.example.bsproject.service.CommunityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.DomainEvents;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AnnounceInfoController {
    @Autowired
    AnnounceInfoService announceInfoService;
    @Autowired
    WebSocket webSocket;


    //用户-管理员查看
    @GetMapping("/getAllAnnounce")
    @JsonView(AnnounceInfo.AnnounceDetailView.class)
    public CommonResult<List<AnnounceInfo>>  adGetAllInfoInList(){
        return CommonResult.success(announceInfoService.list());
    }

    //查看已存在单体
    @GetMapping("/getAnnounce/{id}")
    @JsonView(AnnounceInfo.AnnounceDetailView.class)
   public AnnounceInfo getOneInfo(@PathVariable("id") Long id){
        return announceInfoService.getById(id);
   }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/saveAnnounce")
    @JsonView(AnnounceInfo.AnnounceDetailView.class)
    public CommonResult<AnnounceInfoParam> addInfo(@RequestBody AnnounceInfoParam announceInfoParam){
        return  announceInfoService.saveByParam(announceInfoParam);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("/admin/deleteAnnounce/{id}")
    @JsonView(AnnounceInfo.AnnounceDetailView.class)
    public CommonResult<AnnounceInfo> deleteInfo(@PathVariable("id") Long id){
        return  announceInfoService.removeById(id);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/updateAnnounce")
    @JsonView(AnnounceInfo.AnnounceDetailView.class)
    public CommonResult<AnnounceInfo> updateInfo(@RequestBody AnnounceInfoParam announceInfoParam){
        return  announceInfoService.updateyParam(announceInfoParam);
    }

    //webSocket公告
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/sendNotice")
    public CommonResult<Object> sendNotice(@RequestParam("message") String announcement){
        webSocket.sendAllMessage("系统公告",announcement);
        return CommonResult.success("发送公告成功",announcement);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/admin/sendMessage")
    public CommonResult<Object> sendMessage(@RequestParam("username") String username, @RequestParam("message") String message){
        webSocket.sendOneMessage(username,"管理员私信",message);
        Map<String,String> map=new HashMap<>();
        map.put("username",username);
        map.put("message",message);
        return CommonResult.success("单点发送成功",map);
    }
}
