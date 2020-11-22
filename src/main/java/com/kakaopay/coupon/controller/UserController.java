package com.kakaopay.coupon.controller;

import com.kakaopay.coupon.dto.UserDTO;
import com.kakaopay.coupon.service.UserService;
import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"users"})
@RestController
@RequestMapping("/users")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원 추가 메서드.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserDTO userDTO) {
        if (UserDTO.hasNullDataBeforeSignup(userDTO)) {
            throw new NullPointerException("회원 추가시 필수 데이터를 모두 입력 해야 합니다.");
        }
        userService.register(userDTO);
    }

    /**
     * 회원 수정 메서드.
     */
    @PatchMapping("{userId}")
    public HttpStatus updateAddress(@RequestBody UserUpdateRequest userUpdateRequest) {
        long Id = 0;
        UserDTO userDTO = userUpdateRequest.getUserDTO();

        try {
            userService.updateUser(userDTO);
        } catch (NullPointerException e) {
            log.error("updateUser 실패", e);
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    /**
     * 회원 ID 삭제 메서드.
     */
    @DeleteMapping("{userId}")
    public HttpStatus deleteId(@RequestBody long id) {
        try {
            userService.deleteId(id);
        } catch (RuntimeException e) {
            log.info("deleteID 실패", e);
        }
        return HttpStatus.OK;
    }

    @Getter
    private static class UserUpdateRequest {
        @NonNull
        private UserDTO userDTO;
    }

}
