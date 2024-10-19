package org.example.bsproject.dto.Param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilitiesInfoParam {

    private Long id;

    private Long facId;

    private Boolean status;

    private String extra;

    public boolean isEmpty(){
        return facId==null&&status==null;
    }
}
