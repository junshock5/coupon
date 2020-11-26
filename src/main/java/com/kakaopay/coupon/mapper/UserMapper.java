package com.kakaopay.coupon.mapper;

import com.kakaopay.coupon.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    UserDTO getUserProfile(@Param("id") long id);

    int deleteUserProfile(@Param("id") long id);

    int register(UserDTO userDTO);

    int idCheck(long id);

    int updateUser(UserDTO userDTO);

    List<UserDTO> totalUsers();
}
