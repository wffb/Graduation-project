package org.example.bsproject;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.common.util.JwtUtil;
import org.example.bsproject.common.util.RedisUtil;
import org.example.bsproject.component.WebSocket;
import org.example.bsproject.controller.CommunityController;
import org.example.bsproject.controller.TestController;
import org.example.bsproject.dto.Param.*;
import org.example.bsproject.dto.User;
import org.example.bsproject.mapper.UserMapper;
import org.example.bsproject.service.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

//websocket测试所需
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class BSprojectApplicationTests {
    //基本环境测试
    private  static  final Logger logger = LoggerFactory.getLogger(BSprojectApplicationTests.class);

    @Autowired
    WebSocket webSocket;

    @Autowired
    TestController testController;

    @Autowired
    CommunityController communityController;

    @Autowired
    CommunityInfoService communityInfoService;

    @Autowired
    AnnounceInfoService announceInfoService;

    @Autowired
    PublicFacilitiesService publicFacilitiesService;

    @Autowired
    RepairInfoService repairInfoService;

    @Autowired
    FeeInfoService feeInfoService;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;







    @Test
    void contextLoads() {
        logger.info("hello,my logger");
    }

//    @Test
//    void WSExceptionTest(){
//
//        webSocket.onOpen(null,null);
//    }
//
//    @Test
//    public void testWbController(){
//        testController.testWb();
//    }


    @Test
    public  void communityTest(){
//        List<CommunityInfo> communityInfos= communityController.getAllInfoInList();

//        for( CommunityInfo communityInfo:communityInfos ){
//            System.out.println(communityInfo.toString());
//        }
//增
//        CommunityInfoParam commnityInfoParam =new CommunityInfoParam("第二栋",30,300,"最早的一栋");
//        CommonResult<CommunityInfoParam> communityInfoCommonResult= communityController.addInfo(commnityInfoParam);
//        System.out.println(communityInfoCommonResult.toString());
//删
//        System.out.println(communityController.deleteInfo("一栋"));
//改
//        CommunityInfoParam commnityInfoParam =new CommunityInfoParam("第一栋",30,300,"真的早吗");
//        communityController.updateInfo(commnityInfoParam);
    }

    @Test
    void announceTest(){

//        AnnounceInfoParam announceInfoParam = new AnnounceInfoParam("系统启动公告",
//                "本系统后台预计将在四月初完成设计","系统设计各方面运行顺利", LocalDate.of(2024,4,1));
//        announceInfoService.saveByParam(announceInfoParam);

//          List<AnnounceInfo> list= announceInfoService.list();
//          for(AnnounceInfo announceInfo:list){
//              System.out.println(announceInfo.toString());
//          }
//
//        AnnounceInfoParam announceInfoParam = new AnnounceInfoParam("系统启动公告",
//          "本系统后台预计将在四月初完成设计",null);
//         System.out.println(announceInfoService.updateyParam(announceInfoParam));

    }

    @Test
    void facilitiesTest(){
    //添加测试
//        PublicFacilitiesParam public  FacilitiesParam =new PublicFacilitiesParam(
//                "小区大门",
//                "通行设施",
//                LocalDate.of(2024,4,1),
//                LocalDate.of(2024,4,1),
//                0,
//                "小区第一个完成建设的设施");
//
//        System.out.println(publicFacilitiesService.saveByParam(publicFacilitiesParam));
        //查看测试
        System.out.println(publicFacilitiesService.list());

    }

    @Test
    void repairInfoTest(){
//        RepairInfoParam repairInfoParam =new RepairInfoParam(
//                null,
//                "电脑键盘好像有点灰",
//                false,
//                "第一条记录");

//       System.out.println( repairInfoService.saveByParam(repairInfoParam));

        RepairInfoParam repairInfoParam =new RepairInfoParam();
        repairInfoParam.setId(1L);
        repairInfoParam.setStatus(true);
        repairInfoParam.setExtra("再清理一下");

        System.out.println(repairInfoService.updateByParam(repairInfoParam));
    }

    @Test
    void feeInfoTest(){
        FeeInfoParam param = new FeeInfoParam(
                null,
                "root",
                20,
                "本月管理费",
                false
        );
//       feeInfoService.saveByParam(param);
        param.setStatus(true);
        param.setId("4f795e2af0aff29dae2ca9607203cb3a");

        feeInfoService.updateByParam(param);

    }
    @Test
    void redsitest(){

//    User user = (User) redisTemplate.opsForValue().get("114");
//    System.out.println(user);

//        User user = new User(
//                "115","werb2",null,114,"3901",false
//        );
//        redisTemplate.opsForValue().set(user.getId(),user);

//        System.out.println(redisTemplate.opsForValue().get("115").toString());


        //更新测试
//        redisUtil.set("time",date);

        //时间存取
//        Date date = (Date) redisUtil.get("time");
//         System.out.println(
//                 LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
//         );

        //数据对象存取

//        User user = new User(
//               "123","werb","werb",123,"3901",false,null,null,true
//        );
//        List<String> roles=new ArrayList<String>();
//        roles.add("user");
//
//        LoginUser loginUser = new LoginUser(user,roles);
//
//        System.out.println(loginUser.toString());
//
//
//        redisUtil.set("login:"+user.getUsername(),loginUser);

//        LoginUser loginUser = (LoginUser) redisUtil.get("login:"+"werb");
//        LocalDateTime loginTime = LocalDateTime.ofInstant(
//                loginUser.getLastLoginIn().toInstant(),
//                ZoneId.systemDefault()
//        );
//        System.out.println(LocalDateTime.now().isAfter(loginTime));

        //日期存储
//        LocalDateWrapper time =new LocalDateWrapper(LocalDateTime.now());
//
//        redisUtil.set("wrapper",time);

//        LocalDateWrapper wrapper = (LocalDateWrapper) redisUtil.get("wrapper");
//        System.out.println(wrapper.getTime().toString());

        //删除测试
        redisUtil.del("something happening here");



    }

//    void userTest(){
//        //注册
////        LoginParam userAdminParam =new LoginParam("werb","werb");
////        System.out.println(userAdminService.userRegister(userAdminParam));
//
//        System.out.println(userService.login(userAdminParam));
//    }



//    @Test
//    void JwtTest(){
//        UserDetails userDetails =new Us
//        jwtUtil.generateToken()
//    }


    @Test
    void mpTest(){
        User user =new User()
                .setId(null)
                .setFee(114)
                .setPhone(81191L);

        QueryWrapper<User>queryWrapper=new QueryWrapper<User>()
                .eq("id","3");

        System.out.println(userMapper.update(user,queryWrapper));
    }

    @Test
    void JsonTest(){

        System.out.println(JSON.toJSON(CommonResult.failed("123")));

    }

    @Test
    void userInfoTest(){


        User user = new User();
        user.setEmail("123@qq.com");
        user.setUsername("web");


        QueryWrapper<User> wrapper =new QueryWrapper<User>().eq("username",user);
        userMapper.update(user,wrapper);
    }

    @Test
    void getAllUser(){

        List<String> nameList = userService.list().stream()
                .filter(item->!item.getUserType())
                .map(User::getUsername)
                .collect(Collectors.toList());

        System.out.println(nameList);

    }

}
