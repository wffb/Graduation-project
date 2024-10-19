package org.example.bsproject.dto.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FeeInfoParam {

    private String id;

    private String userName;

    private Integer num;

    private String extra;

    private Boolean status;

    public boolean checkEmpty(){
        return  num==null&&extra==null&&status==null;
    }


}
