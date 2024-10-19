package org.example.bsproject.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.CommunityInfo;
import org.example.bsproject.dto.FacilitiesInfo;
import org.example.bsproject.dto.Param.CommunityInfoParam;
import org.example.bsproject.dto.Param.PublicFacilitiesParam;
import org.example.bsproject.dto.PublicFacilities;
import org.example.bsproject.mapper.PublicFacilitiesMapper;
import org.example.bsproject.service.PublicFacilitiesService;
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
public class PublicFacilitiesServiceImpl extends ServiceImpl<PublicFacilitiesMapper, PublicFacilities> implements PublicFacilitiesService {

    @Override
    public PublicFacilities getByName(String name) {

        return this.getOne( new QueryWrapper<PublicFacilities>().eq("name",name));
    }

    /** 删除对应设施信息
     * @param name
     * @return   CommonResult<CommnityInfo>
     */
    @Override
    public CommonResult<PublicFacilities> deleteByParam(String name) {
        //信息判空
        if(name==null)
            return CommonResult.failed("设施信息不能为空");
        //信息判断是否存在
        if(this.getByName(name)==null)
            return CommonResult.failed("当前设施信息对象不存在");

        QueryWrapper<PublicFacilities> queryWrapper = new QueryWrapper<PublicFacilities>().eq("name",name);

        //删除
        if(this.remove(queryWrapper)){
            return  CommonResult.success("设施信息删除成功",null);
        }
        else{
            return  CommonResult.failed("设施信息删除失败");
        }
    }

    /** 更新对应社区信息
     * @param    param
     * @return   CommonResult<CommnityInfo>
     */
    @Override
    public CommonResult<PublicFacilities> updateyParam(PublicFacilitiesParam param) {
        //信息判空
        if(param==null||param.getName()==null)
            return CommonResult.failed("设施信息不能为空");
        //信息判断是否存在
        if(this.getByName(param.getName())==null)
            return CommonResult.failed("当前设施信息对象不存在");


        if(this.update(
                PublicFacilities.saveParam(param),
                new QueryWrapper<PublicFacilities>().eq("name",param.getName()))){

            return  CommonResult.success("设施信息更新成功",this.getByName(param.getName()));
        }
        else{
            return  CommonResult.failed("设施信息更新失败");
        }
    }

    @Override
    public CommonResult<PublicFacilities> updateyParam(PublicFacilitiesParam param, Long id) {
        //信息判空
        if(id==null)
            return CommonResult.failed("设施信息不能为空");
        //信息判断是否存在
        if(this.getById(id)==null)
            return CommonResult.failed("当前设施信息对象不存在");


        if(this.update(
                PublicFacilities.saveParam(param),
                new QueryWrapper<PublicFacilities>().eq("id",id))){

            return  CommonResult.success("设施信息更新成功",this.getByName(param.getName()));
        }
        else{
            return  CommonResult.failed("设施信息更新失败");
        }

    }


    /** 新增对应社区信息
     * @param param
     * @return
     */
    @Override
    public CommonResult<PublicFacilitiesParam> saveByParam(PublicFacilitiesParam param) {
        //信息判空
        if(param==null||param.getName()==null)
            return CommonResult.failed("设施信息不能为空");
        //信息判断是否存在
        if(this.getByName(param.getName())!=null)
            return CommonResult.failed("当前设施信息对象已经存在");

        if(this.save(PublicFacilities.saveParam(param))){
            return  CommonResult.success("设施信息添加成功",param);
        }
        else{
            return  CommonResult.failed("设施信息添加失败");
        }

    }
}
