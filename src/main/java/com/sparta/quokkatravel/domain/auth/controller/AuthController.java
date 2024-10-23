package com.sparta.quokkatravel.domain.auth.controller;

import com.sparta.quokkatravel.domain.auth.dto.request.SignInRequestDto;
import com.sparta.quokkatravel.domain.auth.dto.request.SignUpRequestDto;
import com.sparta.quokkatravel.domain.auth.dto.response.SignInResponseDto;
import com.sparta.quokkatravel.domain.auth.dto.response.SignUpResponseDto;
import com.sparta.quokkatravel.domain.auth.service.AuthService;
import com.sparta.quokkatravel.domain.common.advice.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "auth 관련 컨트롤러")
public class AuthController {
    private final AuthService authService;

    //회원가입
    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "회원 가입 API")
    public ResponseEntity<?> signUp( //wild card: 뭘 넣어도 괜찮아
            @Valid
            @RequestBody SignUpRequestDto signupRequestDto){
        SignUpResponseDto signupResponseDto = authService.signUp(signupRequestDto);
        return ResponseEntity.ok(ApiResponse.created("회원 가입 성공", signupResponseDto));
    }

    // 로그인
    @PostMapping("/signin")
    @Operation(summary = "로그인", description = "로그인 API")
    public ResponseEntity<?> signIn(
            @Valid
            @RequestBody SignInRequestDto signinRequestDto){
        SignInResponseDto signinResponseDto = authService.signIn(signinRequestDto);
        return ResponseEntity.ok(ApiResponse.success("로그인 성공", signinResponseDto));
    }
}
