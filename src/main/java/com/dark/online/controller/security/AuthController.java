package com.dark.online.controller.security;

import com.dark.online.dto.jwt.JwtRequestDto;
import com.dark.online.dto.mfa.MfaVerificationRequest;
import com.dark.online.dto.security.ChangePasswordDto;
import com.dark.online.dto.user.RegistrationUserDto;
import com.dark.online.service.AccountService;
import com.dark.online.service.AuthService;
import com.dark.online.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//data:image/jpeg;base64,
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.register(registrationUserDto);
    }

    @PostMapping( "/login")
    public ResponseEntity<?> login(@RequestBody JwtRequestDto loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/create2FA")
    public ResponseEntity<?> create2FA() {
        return authService.create2FA();
    }

    @PostMapping("/verifycode")
    public ResponseEntity<?> verifyTotp(@RequestBody MfaVerificationRequest mfaVerificationRequest) {
        return authService.verifyCode(mfaVerificationRequest);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<?> verifyTotp(@RequestBody ChangePasswordDto changePasswordDto) {
        return accountService.changePassword(changePasswordDto);
    }
}
