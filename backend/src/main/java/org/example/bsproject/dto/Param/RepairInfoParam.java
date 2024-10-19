package org.example.bsproject.dto.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RepairInfoParam {
    //用户信息由token提供

    private Long id;

    private String content;

    private Boolean status;

    private String extra;

    public boolean checkEmpty(){
        return  content==null&&extra==null;
    }

}
