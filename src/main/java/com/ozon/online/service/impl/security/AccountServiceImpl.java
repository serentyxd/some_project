package com.ozon.online.service.impl.security;

import com.ozon.online.dto.security.ChangePasswordDto;
import com.ozon.online.dto.user.ChangeNicknameDto;
import com.ozon.online.entity.User;
import com.ozon.online.exception.ErrorResponse;
import com.ozon.online.repository.UserRepository;
import com.ozon.online.service.AccountService;
import com.ozon.online.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        User user = userService.getAuthenticationPrincipalUserByNickname().orElseThrow();

        if (StringUtils.isEmpty(changePasswordDto.getOldPassword())
                || StringUtils.isEmpty(changePasswordDto.getNewPassword())
                || StringUtils.isEmpty(changePasswordDto.getRepeatPassword())) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "PASSWORD_NULL"));
        }
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "OLD_PASSWORD_NOT_MATCH"));
        }
        if (passwordEncoder.matches(changePasswordDto.getNewPassword(), user.getPassword())) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "PASSWORD_MATCHED"));
        }
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getRepeatPassword())) {
            return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "BAD_REPEAT_PASSWORD"));
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "PASSWORD_CHANGED_SUCCESSFULLY"));
    }

    public ResponseEntity<?> changeNickname(@RequestBody ChangeNicknameDto changeNicknameDto) {
        User userOptional = userService.getAuthenticationPrincipalUserByNickname().orElseThrow();
        userOptional.setNickname(changeNicknameDto.getNickname());

        userRepository.save(userOptional);
        return ResponseEntity.ok().body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "NICKNAME_CHANGED_SUCCESSFULLY"));
    }
}