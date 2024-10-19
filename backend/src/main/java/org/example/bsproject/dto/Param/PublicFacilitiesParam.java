package org.example.bsproject.dto.Param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicFacilitiesParam {
    private String name;

    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate setDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate updateDate;

    /**
     *  待处理事务数量
     */
    private Integer status;

    //当前维护计划
    private String extra;
}
