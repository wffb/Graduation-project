package org.example.bsproject.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.Param.FacilitiesInfoParam;
import org.example.bsproject.dto.Param.FeeInfoParam;

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
@TableName("fee_info")
public class FeeInfo implements Serializable {

    public interface FeeInfoSimpleView extends CommonResult.CommonResultView{};
    public interface FeeInfoDetailView extends FeeInfoSimpleView {};

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @JsonView(FeeInfoSimpleView.class)
    private String id;

    @JsonView(FeeInfoDetailView.class)
    private String userName;

    @JsonView(FeeInfoSimpleView.class)
    private Integer num;

    @JsonView(FeeInfoSimpleView.class)
    private String extra;

    @JsonView(FeeInfoSimpleView.class)
    private LocalDate date;

    //缴费是否完成
    @JsonView(FeeInfoSimpleView.class)
    private Boolean status;

    public static FeeInfo saveParam(FeeInfoParam param, LocalDate localDate){
        return  new FeeInfo(
                param.getId(),
                param.getUserName(),
                param.getNum(),
                param.getExtra(),
                localDate,
                param.getStatus());
    }


}
