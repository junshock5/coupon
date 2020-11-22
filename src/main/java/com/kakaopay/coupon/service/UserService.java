package com.kakaopay.coupon.service;

import com.kakaopay.coupon.dto.UserDTO;
import com.kakaopay.coupon.exception.DuplicateIdException;
import com.kakaopay.coupon.exception.user.UserDeleteException;
import com.kakaopay.coupon.exception.user.UserInsertException;
import com.kakaopay.coupon.exception.user.UserUpdateException;
import com.kakaopay.coupon.mapper.UserMapper;
import com.sun.istack.internal.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {

    @Autowired
    private final UserMapper userMapper;

    public UserService(UserMapper userProfileMapper) {
        this.userMapper = userProfileMapper;
    }

    public void register(UserDTO userDTO) {
        boolean duplIdResult = isDuplicatedId(userDTO.getId());
        if (duplIdResult) {
            throw new DuplicateIdException("중복된 아이디 입니다.");
        }
        int insertCount = userMapper.register(userDTO);

        if (insertCount != 1) {
            log.error("insertMember ERROR! {}", userDTO);
            throw new UserInsertException("insertUser ERROR! 유저 추가 메서드를 확인해주세요\n" + "Params : " + userDTO);
        }
    }

    public void updateUser(@NotNull UserDTO userDTO) {
        try {
            userMapper.updateUser(userDTO);
        } catch (UserUpdateException e) {
            log.error("updateAddress ERROR! {}", userDTO);
            throw new UserUpdateException("updateAddress ERROR! 유저 변경 메서드를 확인해주세요\n" + "Params : " + userDTO);
        }
    }

    public void deleteId(long id) {
        if (id != 0) {
            userMapper.deleteUserProfile(id);
        } else {
            log.error("deleteId ERROR! {}", id);
            throw new UserDeleteException("deleteId ERROR! 유저 id 삭제 메서드를 확인해주세요\n" + "Params : " + id);
        }
    }

    public boolean isDuplicatedId(long id) {
        return userMapper.idCheck(id) == 1;
    }
}
