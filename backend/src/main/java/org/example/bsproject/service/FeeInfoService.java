package org.example.bsproject.service;

import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.FeeInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.bsproject.dto.Param.FacilitiesInfoParam;
import org.example.bsproject.dto.Param.FeeInfoParam;
import org.example.bsproject.dto.Param.RepairInfoParam;
import org.example.bsproject.dto.RepairInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
public interface FeeInfoService extends IService<FeeInfo> {


    public CommonResult<List<FeeInfo>> listByName(String username);

    public CommonResult<FeeInfoParam> saveByParam(FeeInfoParam param );


    public CommonResult<FeeInfo> removeById(Long id);

    public CommonResult<FeeInfo> updateByParam(FeeInfoParam param);

}
