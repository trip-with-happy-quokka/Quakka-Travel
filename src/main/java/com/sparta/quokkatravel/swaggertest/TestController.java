package com.sparta.quokkatravel.swaggertest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Tag(name = "Test", description = "게시판 API")
public class TestController {

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "마이페이지 접근시, 회원가입 할 때 사용하는 API")
    public String signUp(@RequestParam int t) {
        return "테스트";
    }
}
