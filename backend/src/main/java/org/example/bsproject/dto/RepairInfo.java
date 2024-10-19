package org.example.bsproject.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
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
import org.example.bsproject.dto.Param.RepairInfoParam;
import org.example.bsproject.dto.Param.UserRepairInfoParam;

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
@TableName("repair_info")
public class RepairInfo implements Serializable {

    public interface RepairInfoSimpleView extends CommonResult.CommonResultView{};
    public interface RepairInfoDetailView extends RepairInfoSimpleView {};

    private static final long serialVersionUID = 1L;


    @JsonView(RepairInfoSimpleView.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @JsonView(RepairInfoSimpleView.class)
    private String userName;

    @JsonView(RepairInfoSimpleView.class)
    private String content;

    @JsonView(RepairInfoSimpleView.class)
    private boolean status;

    @JsonView(RepairInfoSimpleView.class)
    private String extra;

    @JsonView(RepairInfoSimpleView.class)
    @JsonFormat(pattern = "yyyy-MM-dd mm:ss")
    private LocalDateTime submitTime;

    @JsonView(RepairInfoSimpleView.class)
    @JsonFormat(pattern = "yyyy-MM-dd mm:ss")
    private LocalDateTime updateTime;

    //自定他创始时间
    public  static  RepairInfo saveParam (RepairInfoParam param, LocalDateTime time,String name) {
        return new RepairInfo(
                null,
                name,
                param.getContent(),
                param.getStatus(),
                param.getExtra(),
                time,
                LocalDateTime.now()
        );
    }

    public  static  RepairInfo saveParam (UserRepairInfoParam param) {

        RepairInfo repairInfo =new RepairInfo();
        repairInfo.setId(param.getId());
        repairInfo.setContent(param.getContent());

        return  repairInfo;
    }

}
