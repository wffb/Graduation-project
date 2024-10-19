package org.example.bsproject.dto.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoParam {

    private String username;

    private String password;

    private String email;


    private Long phone;


    private Boolean sex;

    boolean isEmpty(){
        return  password==null && email==null && phone==null && sex==null  ;
    }
}
