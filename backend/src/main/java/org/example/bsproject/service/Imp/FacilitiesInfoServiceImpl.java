package org.example.bsproject.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.FacilitiesInfo;
import org.example.bsproject.dto.Param.FacilitiesInfoParam;
import org.example.bsproject.dto.PublicFacilities;
import org.example.bsproject.mapper.FacilitiesInfoMapper;
import org.example.bsproject.mapper.PublicFacilitiesMapper;
import org.example.bsproject.service.FacilitiesInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
@Service
public class FacilitiesInfoServiceImpl extends ServiceImpl<FacilitiesInfoMapper, FacilitiesInfo> implements FacilitiesInfoService {

    @Autowired
    PublicFacilitiesMapper publicFacilitiesMapper;
    @Autowired
    FacilitiesInfoMapper facilitiesInfoMapper;


    /**
     * 通过设施id获取全部的维护记录
     * @param fac_id
     * @return
     */
    @Override
    public CommonResult<List<FacilitiesInfo>>  getByFacId(Long fac_id) {

        QueryWrapper<FacilitiesInfo>queryWrapper=new QueryWrapper<FacilitiesInfo>().eq("fac_id",fac_id);
        return CommonResult.success("设施搜索成功",this.list(queryWrapper));
    }

    /** 删除对应设施信息
     * @param id
     * @return   CommonResult<CommnityInfo>
     */
    @Transactional
    @Override
    public CommonResult<FacilitiesInfo> removeByFacId(Long id) {


        PublicFacilities publicFacilities = publicFacilitiesMapper.selectById(id);
        if(Objects.isNull(publicFacilities))
            return CommonResult.failed("设施信息记录不存在");

        QueryWrapper<FacilitiesInfo> queryWrapper = new QueryWrapper<FacilitiesInfo>().eq("fac_id",id);

        //删除
        if(this.remove(queryWrapper)){
            //更新设施信息
            publicFacilities.setStatus(0);
            publicFacilitiesMapper.updateById(publicFacilities);

            return  CommonResult.success("设施信息删除成功",null);
        }
        else{
            return  CommonResult.failed("设施信息删除失败");
        }
    }

    /** 更新对应设施信息
     * @param    param
     * @return   CommonResult<CommnityInfo>
     */
    @Transactional
    @Override
    public CommonResult<FacilitiesInfo> updateByParam(FacilitiesInfoParam param) {



        //设施是否存在
        if(publicFacilitiesMapper.selectById(param.getFacId())==null)
            return CommonResult.failed("设施id不存在");


        //保存原始状态
        FacilitiesInfo last= facilitiesInfoMapper.selectById(param.getId());
        if(this.update(

                FacilitiesInfo.saveParam(param,null),
                new QueryWrapper<FacilitiesInfo>().eq("id",param.getId()))){

            //如果检修任务状态发送变化
            if(param.getStatus()!=last.getStatus()){
                //更新设施信息
                PublicFacilities publicFacilities = publicFacilitiesMapper.selectById(param.getFacId());
                publicFacilities.setUpdateDate(LocalDate.now());
                //转为完成或者未完成任务
                if(param.getStatus()) publicFacilities.setStatus(publicFacilities.getStatus()-1);
                else publicFacilities.setStatus(publicFacilities.getStatus()+1);
                QueryWrapper<PublicFacilities> publicFacilitiesQueryWrapper =new QueryWrapper<PublicFacilities>()
                        .eq("id",param.getFacId());

                publicFacilitiesMapper.update(publicFacilities,publicFacilitiesQueryWrapper);

            }

            return  CommonResult.success("设施信息更新成功",facilitiesInfoMapper.selectById(param.getId()));
        }
        else{
            return  CommonResult.failed("设施信息更新失败");
        }
    }

    /** 新增对应社区信息
     * @param param
     * @return
     */
    @Transactional
    @Override
    public CommonResult<FacilitiesInfoParam> saveByParam(FacilitiesInfoParam param) {


        //设施是否存在
        if(publicFacilitiesMapper.selectById(param.getFacId())==null)
            return CommonResult.failed("设施id不存在");

        //id自动分配
        FacilitiesInfo info =FacilitiesInfo.saveParam(param, LocalDateTime.now());
        info.setId(null);

        if(this.save(info)){

            if(!param.getStatus()){
                //更新设施本体信息
                PublicFacilities publicFacilities = publicFacilitiesMapper.selectById(param.getFacId());

                publicFacilities.setStatus(publicFacilities.getStatus()+1);
                publicFacilities.setUpdateDate(LocalDate.now());

                QueryWrapper<PublicFacilities> publicFacilitiesQueryWrapper =new QueryWrapper<PublicFacilities>()
                        .eq("id",param.getFacId());

                publicFacilitiesMapper.update(publicFacilities,publicFacilitiesQueryWrapper);
            }

            return  CommonResult.success("设施信息添加成功",param);
        }
        else{
            return  CommonResult.failed("设施信息添加失败");
        }

    }

    @Transactional
    @Override
    public CommonResult<List<FacilitiesInfo>> deleteById(Long id) {
        //维修记录否存在
        FacilitiesInfo info = facilitiesInfoMapper.selectById(id);
        if (Objects.isNull(info))
            return CommonResult.failed("该维修记录不存在");

        facilitiesInfoMapper.deleteById(id);
        if (!info.getStatus()) {
            PublicFacilities publicFacilities = publicFacilitiesMapper.selectById(info.getFacId());
            //待处理任务-1
            publicFacilities.setStatus(publicFacilities.getStatus()-1);

            publicFacilitiesMapper.updateById(publicFacilities);
        }
        return CommonResult.success("维修记录删除成功");
    }

}
