package com.kakaopay.coupon.mapper;

import com.kakaopay.coupon.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    public UserDTO getUserProfile(@Param("id") long id);

    int deleteUserProfile(@Param("id") long id);

    public int register(UserDTO userDTO);

    int idCheck(long id);

    public int updateUser(UserDTO userDTO);

}
