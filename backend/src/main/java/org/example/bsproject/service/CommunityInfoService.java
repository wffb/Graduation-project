package org.example.bsproject.service;

import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.CommunityInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.bsproject.dto.Param.CommunityInfoParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
public interface CommunityInfoService extends IService<CommunityInfo> {

    public CommunityInfo getByName(String name);

    public CommonResult<CommunityInfoParam> saveByParam(CommunityInfoParam communityInfo);

    CommonResult<CommunityInfo> removeByParam(String name);

    CommonResult<CommunityInfo> updateyParam(CommunityInfoParam communityInfo);
}
