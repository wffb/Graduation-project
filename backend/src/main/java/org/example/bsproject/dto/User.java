package org.example.bsproject.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.example.bsproject.common.api.CommonResult;
import org.example.bsproject.dto.Param.AdminUserInfoParam;
import org.example.bsproject.dto.Param.LoginParam;
import org.example.bsproject.dto.Param.UserInfoParam;

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
@TableName("user")
public class User implements Serializable {

    public interface UserSimpleView extends CommonResult.CommonResultView{};
    public interface UserDetailView extends UserSimpleView {};

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @JsonView(UserDetailView.class)
    private String id;

    @JsonView(UserSimpleView.class)
    private String username;

    @JsonView(UserDetailView.class)
    private String password;

    //待缴物业费
    @JsonView(UserSimpleView.class)
    private Integer fee;

    @JsonView(UserSimpleView.class)
    private String location;

    @JsonView(UserSimpleView.class)
    private Boolean userType;

    @JsonView(UserSimpleView.class)
    private String email;

    @JsonView(UserSimpleView.class)
    private Long phone;

    @JsonView(UserSimpleView.class)
    private Boolean sex;


    //字段转换-补完
    public static User getParam(LoginParam param, boolean isAdmin){

        return new User()
                .setUsername(param.getUsername())
                .setPassword(param.getPassword())
                .setUserType(isAdmin)
                .setFee(0)
                .setId(null)
                .setLocation(null)
                .setPhone(null)
                .setSex(null);
    }

    public static User getParam(AdminUserInfoParam param){

        return new User()
                .setUsername(param.getUsername())
                .setPassword(param.getPassword())
                .setUserType(null)
                .setFee(0)
                .setId(null)
                .setLocation(param.getLocation())
                .setPhone(param.getPhone())
                .setEmail(param.getEmail())
                .setSex(param.getSex());
    }

    public static User getParam(UserInfoParam param){

        return new User()
                .setUsername(null)
                .setPassword(param.getPassword())
                .setUserType(null)
                .setFee(null)
                .setId(null)
                .setLocation(null)
                .setEmail(param.getEmail())
                .setPhone(param.getPhone())
                .setSex(param.getSex());
    }


}
