package org.example.bsproject.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.Param.CommunityInfoParam;

/**
 * <p>
 * 
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
@TableName("community_info")
public class CommunityInfo implements Serializable {

    //视图限制
    public interface CommunitySimpleView extends CommonResult.CommonResultView{};
    public interface CommunityDetailView extends CommunitySimpleView{};

    private static final long serialVersionUID = 1L;

    @JsonView(CommunitySimpleView.class)
    private String name;

    @JsonView(CommunityDetailView.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @JsonView(CommunitySimpleView.class)
    private Integer floors;

    @JsonView(CommunitySimpleView.class)
    private Integer numOfPeople;

    @JsonView(CommunitySimpleView.class)
    //建筑额外介绍
    private String extra;

    public  static  CommunityInfo saveParam (CommunityInfoParam commnityInfoParam){
        return new CommunityInfo(
                commnityInfoParam.getName(),
                null,
                commnityInfoParam.getFloors(),
                commnityInfoParam.getNumOfPeople(),
                commnityInfoParam.getExtra());
    }


}
