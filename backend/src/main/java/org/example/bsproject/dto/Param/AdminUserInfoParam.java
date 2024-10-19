package org.example.bsproject.dto.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员修改用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AdminUserInfoParam {

    private String username;

    private String password;


    private Integer fee;


    private String location;



    private String email;


    private Long phone;


    private Boolean sex;

    public boolean isEmpty(){
        //username不能为空
        if(username==null) return  true;
        else return  password==null && email==null && phone==null && sex==null
                && fee==null && location==null;
    }

}
