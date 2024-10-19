package org.example.bsproject.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.Param.FacilitiesInfoParam;
import org.example.bsproject.dto.Param.PublicFacilitiesParam;

/**
 * <p>
 * 公共设施信息 依靠不重复的name作为识别量
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("public_facilities")
public class PublicFacilities implements Serializable {

    public interface PublicFacilitiesSimpleView extends CommonResult.CommonResultView{};
    public interface PublicFacilitiesDetailView extends PublicFacilitiesSimpleView {};

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonView(PublicFacilitiesDetailView.class)
    private Long id;

    @JsonView(PublicFacilitiesSimpleView.class)
    private String name;

    @JsonView(PublicFacilitiesSimpleView.class)
    private String type;

    @JsonView(PublicFacilitiesSimpleView.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate setDate;

    @JsonView(PublicFacilitiesSimpleView.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;

    /**
     *  待处理事务数量
     */
    @JsonView(PublicFacilitiesSimpleView.class)
    private Integer status;

    @JsonView(PublicFacilitiesSimpleView.class)
    //当前维护计划
    private String extra;

    public  static  PublicFacilities saveParam (PublicFacilitiesParam param) {
        return new PublicFacilities(
                null,
                param.getName(),
                param.getType(),
                param.getSetDate(),
                param.getUpdateDate(),
                param.getStatus(),
                param.getExtra());
    }


}
