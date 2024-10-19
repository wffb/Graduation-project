package org.example.bsproject.service;

import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.Param.RepairInfoParam;
import org.example.bsproject.dto.Param.UserRepairInfoParam;
import org.example.bsproject.dto.RepairInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
public interface RepairInfoService extends IService<RepairInfo> {



    public CommonResult<List<RepairInfo> > listByUsername(String username);

    public CommonResult<RepairInfoParam> saveByParam(RepairInfoParam param,String name);

    public CommonResult<RepairInfo> updateByParam(RepairInfoParam param);

    public CommonResult<RepairInfo> adminRemoveById(Long id);

    public CommonResult<RepairInfo> userRemoveById(Long id,String username);

    public CommonResult<RepairInfo> userUpdateByParam(UserRepairInfoParam param,String username);


}
