package org.example.bsproject.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.CommunityInfo;
import org.example.bsproject.mapper.CommunityInfoMapper;
import org.example.bsproject.dto.Param.CommunityInfoParam;
import org.example.bsproject.service.CommunityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
@Service
public class CommunityInfoServiceImpl extends ServiceImpl<CommunityInfoMapper, CommunityInfo> implements CommunityInfoService {

    @Override
    public CommunityInfo getByName(String name) {

       return this.getOne( new QueryWrapper<CommunityInfo>().eq("name",name));
    }

    /** 删除对应社区信息
     * @param name
     * @return   CommonResult<CommnityInfo>
     */
    @Override
    public CommonResult<CommunityInfo> removeByParam(String name) {
        //信息判空
        if(name==null)
            return CommonResult.failed("社区信息不能为空");
        //信息判断是否存在
        if(this.getByName(name)==null)
            return CommonResult.failed("当前社区信息对象不存在");

        QueryWrapper<CommunityInfo> queryWrapper = new QueryWrapper<CommunityInfo>().eq("name",name);

        //删除
        if(this.remove(queryWrapper)){
            return  CommonResult.success("社区信息删除成功",null);
        }
        else{
            return  CommonResult.failed("社区信息删除失败");
        }
    }

    /** 更新对应社区信息
     * @param    communityInfo
     * @return   CommonResult<CommnityInfo>
     */
    @Override
    public CommonResult<CommunityInfo> updateyParam(CommunityInfoParam communityInfo) {
        //信息判空
        if(communityInfo==null||communityInfo.getName()==null)
            return CommonResult.failed("社区信息不能为空");
        //信息判断是否存在
        if(this.getByName(communityInfo.getName())==null)
            return CommonResult.failed("当前社区信息对象不存在");


        if(this.update(
                CommunityInfo.saveParam(communityInfo),
                new QueryWrapper<CommunityInfo>().eq("name",communityInfo.getName()))){

            return  CommonResult.success("社区信息更新成功",this.getByName(communityInfo.getName()));
        }
        else{
            return  CommonResult.failed("社区信息更新失败");
        }
    }

    /** 新增对应社区信息
     * @param communityInfo
     * @return
     */
    @Override
    public CommonResult<CommunityInfoParam> saveByParam(CommunityInfoParam communityInfo) {
        //信息判空
        if(communityInfo==null||communityInfo.getName()==null)
            return CommonResult.failed("社区信息不能为空");
        //信息判断是否存在
        if(this.getByName(communityInfo.getName())!=null)
            return CommonResult.failed("当前社区信息对象已存在");
        //插入
       if(this.save(CommunityInfo.saveParam(communityInfo))){
           return  CommonResult.success("社区信息添加成功",communityInfo);
       }
       else{
           return  CommonResult.failed("社区信息添加失败");
       }

    }


}
