package org.example.bsproject.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.FeeInfo;
import org.example.bsproject.dto.Param.FeeInfoParam;
import org.example.bsproject.dto.RepairInfo;
import org.example.bsproject.dto.User;
import org.example.bsproject.mapper.FacilitiesInfoMapper;
import org.example.bsproject.mapper.FeeInfoMapper;
import org.example.bsproject.mapper.UserMapper;
import org.example.bsproject.service.FeeInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.bsproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
@Service
public class FeeInfoServiceImpl extends ServiceImpl<FeeInfoMapper, FeeInfo> implements FeeInfoService {
    /**
     * 通过用户名获取对应的记录
     * @param username
     * @return
     */

    @Autowired
    FeeInfoMapper feeInfoMapper;

    @Autowired
    UserService userService;

    @Override
    public CommonResult<List<FeeInfo>> listByName(String username) {
        if(username==null)
            return CommonResult.failed("用户名不能为空");

        QueryWrapper<FeeInfo> queryWrapper=new QueryWrapper<FeeInfo>().eq("user_name",username);
        return CommonResult.success("缴费信息搜索成功",this.list(queryWrapper));
    }

    @Transactional
    @Override
    public CommonResult<FeeInfoParam> saveByParam(FeeInfoParam param) {
        //批处理命令
        if(param.getUserName().equals("**")){

            List<FeeInfo> nameList = userService.list().stream()
                    .filter(item->!item.getUserType())
                    .map(item-> {
                        FeeInfo info = FeeInfo.saveParam(param, LocalDate.now());
                        info.setUserName(item.getUsername());
                        return info;
                    })
                    .collect(Collectors.toList());

            if(this.saveBatch(nameList))
                return CommonResult.success("缴费信息添加成功");
            else
                return  CommonResult.failed("缴费信息添加失败");

        }



        //检查用户信息
        if(!userService.userExist(param.getUserName()))
            return CommonResult.failed("用户不存在");
        if(userService.isAdmin(param.getUserName()))
            return CommonResult.failed("权限不足，收费对象不能为管理员");

        //信息存储
        if(this.save(FeeInfo.saveParam(param, LocalDate.now()))) {

            userService.updateUserFee(param.getUserName(),param.getNum());

            return  CommonResult.success("缴费信息添加成功",param);
        }
        else{
            return  CommonResult.failed("缴费信息添加失败");
        }
    }

    @Transactional
    @Override
    public CommonResult<FeeInfo> removeById(Long id) {

        QueryWrapper<FeeInfo>queryWrapper=new QueryWrapper<FeeInfo>().eq("id",id);
        FeeInfo feeInfo=feeInfoMapper.selectOne(queryWrapper);
        if(Objects.isNull(feeInfo))
            return CommonResult.failed("所要删除信息记录不存在");

        //删除
        if(this.remove(queryWrapper)){
            //删除未完成缴费
            if(!feeInfo.getStatus())
                userService.updateUserFee(feeInfo.getUserName(),-1* feeInfo.getNum());

            return  CommonResult.success("记录信息删除成功",null);
        }
        else{
            return  CommonResult.failed("记录信息删除失败");
        }
    }

    @Transactional
    @Override
    public CommonResult<FeeInfo> updateByParam(FeeInfoParam param) {


        //用户是否存在
        if(!userService.userExist(param.getUserName()))
            return CommonResult.failed("用户不存在");

        QueryWrapper<FeeInfo>queryWrapper=new QueryWrapper<FeeInfo>().eq("id",param.getId());
        FeeInfo feeInfo=feeInfoMapper.selectOne(queryWrapper);
        if(Objects.isNull(feeInfo))
            return CommonResult.failed("所要更新信息记录不存在");


        if(this.update(
                FeeInfo.saveParam(param, null).setUserName(null),
                queryWrapper)){
            //发生收费金额变化
            if(!Objects.isNull(param.getNum())&& !param.getNum().equals(feeInfo.getNum()) &&!feeInfo.getStatus()){
                //更改
                Integer changNum= param.getNum()-feeInfo.getNum();
                userService.updateUserFee(feeInfo.getUserName(),changNum);
            }
            //支付状态发送变化
            else if(!Objects.isNull(param.getStatus())&& param.getStatus()!=feeInfo.getStatus()){
                Integer changNum= -1*feeInfo.getNum();

                userService.updateUserFee(feeInfo.getUserName(),changNum);
            }

            return  CommonResult.success("缴费信息更新成功",feeInfoMapper.selectById(param.getId()));
        }
        else{
            return  CommonResult.failed("缴费信息更新失败");
        }
    }
}
