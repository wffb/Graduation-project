package org.example.bsproject.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.Param.RepairInfoParam;
import org.example.bsproject.dto.Param.UserRepairInfoParam;
import org.example.bsproject.dto.RepairInfo;
import org.example.bsproject.dto.User;
import org.example.bsproject.mapper.RepairInfoMapper;
import org.example.bsproject.mapper.UserMapper;
import org.example.bsproject.service.RepairInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.bsproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
@Service
public class RepairInfoServiceImpl extends ServiceImpl<RepairInfoMapper, RepairInfo> implements RepairInfoService {

    @Autowired
    UserService userService;
    @Autowired
    RepairInfoMapper repairInfoMapper;


    /**
     * 通过用户名获取全部其提交的报修记录
     * @param username
     * @return
     */
    @Override
    public CommonResult<List<RepairInfo> > listByUsername(String username) {
        if(username==null)
            return CommonResult.failed("用户名不能为空");

        QueryWrapper<RepairInfo>queryWrapper=new QueryWrapper<RepairInfo>().eq("user_name",username);
        return CommonResult.success("设施搜索成功",this.list(queryWrapper));
    }

    /** 删除对应保修信息
     * @param id
     * @return   CommonResult<CommnityInfo>
     */
    @Override
    public CommonResult<RepairInfo> adminRemoveById(Long id) {

        //信息判空
        if(id==null)
            return CommonResult.failed("信息id不能为空");
        if(this.getById(id)==null)
            return CommonResult.failed("所要删除信息记录不存在");

        QueryWrapper<RepairInfo> queryWrapper = new QueryWrapper<RepairInfo>().eq("id",id);

        //删除
        if(this.remove(queryWrapper)){
            return  CommonResult.success("记录信息删除成功");
        }
        else{
            return  CommonResult.failed("记录信息删除失败");
        }
    }

    @Override
    public CommonResult<RepairInfo> userRemoveById(Long id,String username) {

        //信息判空
        if(id==null)
            return CommonResult.failed("信息id不能为空");

        RepairInfo repairInfo= this.getById(id);
        if(!repairInfo.getUserName().equals(username))
            return  CommonResult.failed("权限不足，用户只能删除自身提交的对应记录");

        //删除
        if(this.removeById(id)){
            return  CommonResult.success("记录信息删除成功");
        }
        else{
            return  CommonResult.failed("记录信息删除失败");
        }
    }

    @Override
    public CommonResult<RepairInfo> userUpdateByParam(UserRepairInfoParam param, String username) {
        //信息判空
        if(param.isEmpty())
            return CommonResult.failed("信息id不能为空");

        RepairInfo repairInfo= this.getById(param.getId());
        if(!repairInfo.getUserName().equals(username))
            return  CommonResult.failed("权限不足，用户只能更改自身提交的对应记录");


        QueryWrapper<RepairInfo> queryWrapper = new QueryWrapper<RepairInfo>().eq("id",param.getId());
        //删除
        if(repairInfoMapper.update(RepairInfo.saveParam(param),queryWrapper)!=0){
            return  CommonResult.success("记录信息删除成功");
        }
        else{
            return  CommonResult.failed("记录信息删除失败");
        }
    }


    /** 更新对应保修信息
     * @param    param
     * @return   CommonResult<CommnityInfo>
     */
    @Override
    public CommonResult<RepairInfo> updateByParam(RepairInfoParam param) {

        //信息判空
        if(param==null||param.checkEmpty()||param.getId()==null)
            return CommonResult.failed("保修信息不能为空");

        //用户是否存在
        QueryWrapper<RepairInfo> queryWrapper = new QueryWrapper<RepairInfo>().eq("id",param.getId());
        if(repairInfoMapper.selectCount(queryWrapper)<=0)
            return CommonResult.failed("对应保修信息不存在");


        if(this.update(
                RepairInfo.saveParam(param, null,null),
                new QueryWrapper<RepairInfo>().eq("id",param.getId()))){

            return  CommonResult.success("设施信息更新成功",repairInfoMapper.selectById(param.getId()));
        }
        else{
            return  CommonResult.failed("设施信息更新失败");
        }
    }

    /** 新增对保修信息
     * @param param
     * @return
     */
    @Override
    public CommonResult<RepairInfoParam> saveByParam(RepairInfoParam param,String name) {
        //信息判空
        if(param==null||param.checkEmpty()||name==null)
            return CommonResult.failed("保修信息不能为空");

        //用户是否存在
        if(!userService.userExist(name))
            return CommonResult.failed("用户不存在");


        if(this.save(RepairInfo.saveParam(param,LocalDateTime.now(),name))){
            return  CommonResult.success("设施信息添加成功",param);
        }
        else{
            return  CommonResult.failed("设施信息添加失败");
        }

    }



}
