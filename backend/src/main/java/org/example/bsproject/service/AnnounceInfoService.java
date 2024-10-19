package org.example.bsproject.service;

import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.AnnounceInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.bsproject.dto.CommunityInfo;
import org.example.bsproject.dto.Param.AnnounceInfoParam;
import org.example.bsproject.dto.Param.CommunityInfoParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
public interface AnnounceInfoService extends IService<AnnounceInfo> {
    public CommonResult<List<AnnounceInfo>> getByTitle(String name);

    public CommonResult<AnnounceInfoParam> saveByParam(AnnounceInfoParam communityInfo);

    public CommonResult<AnnounceInfo> removeById(Long id);

    public CommonResult<AnnounceInfo> updateyParam(AnnounceInfoParam communityInfo);
}
