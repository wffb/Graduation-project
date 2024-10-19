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
import lombok.experimental.Accessors;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.Param.AnnounceInfoParam;
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

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("announce_info")
public class AnnounceInfo implements Serializable {

    public interface AnnounceSimpleView extends CommonResult.CommonResultView{};
    public interface AnnounceDetailView extends AnnounceSimpleView {};

    private static final long serialVersionUID = 1L;

    @JsonView(AnnounceDetailView.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @JsonView(AnnounceSimpleView.class)
    private String title;

    @JsonView(AnnounceSimpleView.class)
    private String content;

    @JsonView(AnnounceSimpleView.class)
    private String extra;

    @JsonView(AnnounceSimpleView.class)
    private LocalDate date;

    public  static  AnnounceInfo saveParam (AnnounceInfoParam announceInfoParam){
        return new AnnounceInfo(
                null,
                announceInfoParam.getTitle(),
                announceInfoParam.getContent(),
                announceInfoParam.getExtra(),
               LocalDate.now()
        );
    }



}
