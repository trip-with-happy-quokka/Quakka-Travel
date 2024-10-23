package com.sparta.quokkatravel.domain.auth.service;

import com.sparta.quokkatravel.domain.auth.dto.request.SignInRequestDto;
import com.sparta.quokkatravel.domain.auth.dto.request.SignUpRequestDto;
import com.sparta.quokkatravel.domain.auth.dto.response.SignInResponseDto;
import com.sparta.quokkatravel.domain.auth.dto.response.SignUpResponseDto;
import com.sparta.quokkatravel.domain.common.config.JwtUtil;
import com.sparta.quokkatravel.domain.common.config.PasswordEncoder;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if(userRepository.existsByEmail(signUpRequestDto.getEmail())){
            throw new InvalidRequestStateException("이미 존재하는 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        UserRole userRole = UserRole.of(signUpRequestDto.getUserRole());
        User newUser = new User(
                signUpRequestDto.getEmail(),
                encodedPassword,
                signUpRequestDto.getName(),
                signUpRequestDto.getPhoneNumber(),
                userRole
        );
        User savedUser = userRepository.save(newUser);
        String bearerToken = jwtUtil.createToken(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getName(),
                savedUser.getPhoneNumber(),
                userRole
        );
        return new SignUpResponseDto(bearerToken);
    }

    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        User user = userRepository.findByEmail(signInRequestDto.getEmail()).orElseThrow(
                ()-> new InvalidRequestStateException("가입되지 않은 이메일 유저입니다.")
        );

        if(!passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword())){
            throw new InvalidRequestStateException("잘못된 비밀번호 입니다.");
        }
        String bearerToken = jwtUtil.createToken(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPhoneNumber(),
                user.getUserRole()
        );
        return new SignInResponseDto(bearerToken);
    }
}
