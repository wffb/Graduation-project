package org.example.bsproject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.bsproject.dto.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author werb
 * @since 2024-03-23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    public  User selectByUsernameUser(@Param("username") String username);
}
