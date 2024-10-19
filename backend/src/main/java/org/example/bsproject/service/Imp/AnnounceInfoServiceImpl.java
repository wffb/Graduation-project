package org.example.bsproject.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.AnnounceInfo;
import org.example.bsproject.dto.Param.AnnounceInfoParam;
import org.example.bsproject.mapper.AnnounceInfoMapper;
import org.example.bsproject.service.AnnounceInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class AnnounceInfoServiceImpl extends ServiceImpl<AnnounceInfoMapper, AnnounceInfo> implements AnnounceInfoService {


    /** 删除对应公告信息
     * @param id
     * @return   CommonResult<CommnityInfo>
     */
    @Override
    public CommonResult<AnnounceInfo> removeById(Long id) {
        //信息判空
        if(id==null)
            return CommonResult.failed("公告信息不能为空");
        //信息判断是否存在
        if(this.getById(id)==null)
            return CommonResult.failed("公告社区信息对象不存在");

        QueryWrapper<AnnounceInfo> queryWrapper = new QueryWrapper<AnnounceInfo>().eq("id",id);

        //删除
        if(this.remove(queryWrapper)){
            return  CommonResult.success("公告信息删除成功",null);
        }
        else{
            return  CommonResult.failed("公告信息删除失败");
        }
    }

    /** 更新对应社区信息
     * @param    announceInfoParam
     * @return   CommonResult<CommnityInfo>
     */
    @Override
    public CommonResult<AnnounceInfo> updateyParam(AnnounceInfoParam announceInfoParam) {
        //信息判空
        if(announceInfoParam==null||announceInfoParam.getId()==null)
            return CommonResult.failed("公告信息不能为空");
        //信息判断是否存在
        if(this.getById(announceInfoParam.getId())==null)
            return CommonResult.failed("公告社区信息对象不存在");


        if(this.update(
                AnnounceInfo.saveParam(announceInfoParam),
                new QueryWrapper<AnnounceInfo>().eq("id",announceInfoParam.getId()))){

            return  CommonResult.success("公告信息更新成功",this.getById(announceInfoParam.getId()));
        }
        else{
            return  CommonResult.failed("公告信息更新失败");
        }
    }

    @Override
    public CommonResult<List<AnnounceInfo>> getByTitle(String name) {
        if(name==null)
            return CommonResult.failed("公告名不能为空");

        QueryWrapper<AnnounceInfo>queryWrapper=new QueryWrapper<AnnounceInfo>().eq("title",name);

        return CommonResult.success("公告搜索成功",this.list(queryWrapper));
    }

    /** 新增对应社区信息
     * @param announceInfoParam
     * @return
     */
    @Override
    public CommonResult<AnnounceInfoParam> saveByParam(AnnounceInfoParam announceInfoParam) {
        //信息判空
        if(announceInfoParam==null||announceInfoParam.checkEmpty())
            return CommonResult.failed("公告信息不能为空");

        //插入
        if(this.save(AnnounceInfo.saveParam(announceInfoParam))){
            return  CommonResult.success("公告信息添加成功",announceInfoParam);
        }
        else{
            return  CommonResult.failed("公告信息添加失败");
        }

    }

}
