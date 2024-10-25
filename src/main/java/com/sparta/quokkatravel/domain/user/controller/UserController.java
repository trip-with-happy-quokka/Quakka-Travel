package com.sparta.quokkatravel.domain.user.controller;

import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import com.sparta.quokkatravel.domain.common.config.JwtUtil;
import com.sparta.quokkatravel.domain.common.dto.CustomUserDetails;
import com.sparta.quokkatravel.domain.user.dto.UserSignupRequestDto;
import com.sparta.quokkatravel.domain.user.dto.UserResponseDto;
import com.sparta.quokkatravel.domain.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
@Tag(name = "User", description = "User 관련 컨트롤러")
public class UserController {
    private final UserServiceImpl userService;
    private final JwtUtil jwtUtil;

    // 회원 가입
    @PostMapping
    @Operation(summary = "회원가입", description = "회원 가입하는 API")
    public ResponseEntity<ApiResponse<UserResponseDto>> signup(
            @Valid
            @RequestBody UserSignupRequestDto userSignupRequestDto) {
        UserResponseDto user = userService.signup(userSignupRequestDto);
        return ResponseEntity.ok(ApiResponse.success("회원가입 성공",user));
    }

    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인하는 API")
    public ResponseEntity<ApiResponse<String>> login(
            @RequestBody UserSignupRequestDto userSignupRequestDto){
        String token = userService.login(userSignupRequestDto);
        return ResponseEntity.ok(ApiResponse.success("로그인 성공", token));
    }

    // 계정 삭제
    @PatchMapping("/delete")
    @Operation(summary = "유저 삭제", description = "유저 삭제하는 API")
    public ResponseEntity<ApiResponse<String>> logout(
            @AuthenticationPrincipal CustomUserDetails authUser,
            @RequestBody UserSignupRequestDto userSignupRequestDto
    ){
        log.info("user:{}",authUser);
        String email = authUser.getEmail();
        userService.deleteUser(email, userSignupRequestDto.getPassword());
        return ResponseEntity.ok(ApiResponse.success("회원 탈퇴 성공"));
    }
}