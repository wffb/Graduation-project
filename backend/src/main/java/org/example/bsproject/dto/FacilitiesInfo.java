package org.example.bsproject.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.Param.CommunityInfoParam;
import org.example.bsproject.dto.Param.FacilitiesInfoParam;

/**
 * <p>
 * 公共设施维修检查记录类
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
@Data
@AllArgsConstructor

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("facilities_info")
public class FacilitiesInfo implements Serializable {

    public interface FacilitiesInfoSimpleView extends CommonResult.CommonResultView{};
    public interface FacilitiesInfoDetailView extends FacilitiesInfoSimpleView {};

    private static final long serialVersionUID = 1L;

    @JsonView(FacilitiesInfoDetailView.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @JsonView(FacilitiesInfoSimpleView.class)
    private LocalDateTime setTime;

    @JsonView(FacilitiesInfoSimpleView.class)
    private LocalDateTime updateTime;

    @JsonView(FacilitiesInfoSimpleView.class)
    private Long facId;

    /**
     *  分为 处理中 已完成两种状态
     */
    @JsonView(FacilitiesInfoSimpleView.class)
    private Boolean status;

    @JsonView(FacilitiesInfoSimpleView.class)
    private String extra;

    //自定义创建时间
    public  static  FacilitiesInfo saveParam (FacilitiesInfoParam param,LocalDateTime time) {
        return new FacilitiesInfo(
                param.getId(),
                time,
                LocalDateTime.now(),
                param.getFacId(),
                param.getStatus(),
                param.getExtra());
    }


}


