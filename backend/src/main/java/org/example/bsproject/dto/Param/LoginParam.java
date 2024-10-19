package org.example.bsproject.dto.Param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginParam {

    private  String username;
    private  String password;

    public boolean isEmpty(){
        return username==null||password==null;
    }
}
