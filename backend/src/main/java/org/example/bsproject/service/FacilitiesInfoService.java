package org.example.bsproject.service;

import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.FacilitiesInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.bsproject.dto.Param.FacilitiesInfoParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
public interface FacilitiesInfoService extends IService<FacilitiesInfo> {
    public CommonResult<List<FacilitiesInfo>> getByFacId(Long fac_id);

    public CommonResult<FacilitiesInfo> removeByFacId(Long id);

    public CommonResult<FacilitiesInfo> updateByParam(FacilitiesInfoParam param);

    public CommonResult<FacilitiesInfoParam> saveByParam(FacilitiesInfoParam param);

    public CommonResult<List<FacilitiesInfo>> deleteById(Long id);


}
