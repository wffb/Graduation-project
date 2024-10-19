package org.example.bsproject.service;

import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.CommunityInfo;
import org.example.bsproject.dto.Param.CommunityInfoParam;
import org.example.bsproject.dto.Param.PublicFacilitiesParam;
import org.example.bsproject.dto.PublicFacilities;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
public interface PublicFacilitiesService extends IService<PublicFacilities> {

    public PublicFacilities getByName(String name);

    public CommonResult<PublicFacilitiesParam> saveByParam(PublicFacilitiesParam publicFacilitiesParam);

    public CommonResult<PublicFacilities> deleteByParam(String name);

    CommonResult<PublicFacilities> updateyParam(PublicFacilitiesParam publicFacilitiesParam);

    CommonResult<PublicFacilities> updateyParam(PublicFacilitiesParam publicFacilitiesParam,Long id);



}
