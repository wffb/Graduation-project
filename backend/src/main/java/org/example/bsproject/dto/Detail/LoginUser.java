package org.example.bsproject.dto.Detail;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bsproject.dto.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor

//禁用UserDetail设置 getter/setter的json序列化
@JsonIgnoreProperties({"enabled","accountNonExpired", "accountNonLocked", "credentialsNonExpired", "authorities","password","username"})
public class LoginUser implements UserDetails {

    private User user;

    //存储权限信息
    private List<String> permissions;

    @JsonIgnore
    //存储SpringSecurity所需要的权限信息的集合
    private List<GrantedAuthority> authorities;


    private LocalDateTime lastLoginIn;

    public LoginUser(User user,List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
        this.lastLoginIn=LocalDateTime.now();
    }

    public LoginUser(User user) {
        this.user = user;

        List<String>auths = new ArrayList<String>();
        if(user.getUserType())
            auths.add("admin");
        else
            auths.add("user");
        this.permissions = auths;

        this.lastLoginIn=LocalDateTime.now();
    }
    //获取权限列表
    @Override
    public  Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null){
            return authorities;
        }
        //把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
        authorities = permissions.stream().
                map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return authorities;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}