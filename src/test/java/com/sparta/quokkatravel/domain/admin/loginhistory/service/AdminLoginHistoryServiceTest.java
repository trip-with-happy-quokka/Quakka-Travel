package com.sparta.quokkatravel.domain.admin.loginhistory.service;

import com.sparta.quokkatravel.domain.admin.loginhistory.dto.AdminLoginHistoryResponseDto;
import com.sparta.quokkatravel.domain.admin.loginhistory.entity.LoginHistory;
import com.sparta.quokkatravel.domain.admin.loginhistory.repository.LoginHistoryRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminLoginHistoryServiceTest {

    @Mock
    private LoginHistoryRepository loginHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminLoginHistoryService adminLoginHistoryService;

    @Test
    void 모든_로그인_기록_조회_테스트() {
        // given
        User user = User.builder()
                .id(1L)
                .nickname("TestUser")
                .build();

        LoginHistory loginHistory = LoginHistory.builder()
                .user(user)
                .ipAddress("127.0.0.1")
                .loginTime(LocalDateTime.now())
                .build();

        List<LoginHistory> loginHistories = List.of(loginHistory);

        given(loginHistoryRepository.findAllByOrderByLoginTimeDesc()).willReturn(loginHistories);

        // when
        List<AdminLoginHistoryResponseDto> result = adminLoginHistoryService.getAllLoginHistory();

        // then
        assertEquals(1, result.size()); // 반환된 기록 개수가 1인지 검증
        assertEquals("TestUser", result.get(0).getNickname()); // 닉네임이 "TestUser"인지 검증
        verify(loginHistoryRepository, times(1)).findAllByOrderByLoginTimeDesc(); // 호출 횟수 검증
    }

    @Test
    void 특정_사용자_로그인_기록_조회_테스트() {
        // given
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .nickname("TestUser")
                .build();

        LoginHistory loginHistory = LoginHistory.builder()
                .user(user)
                .ipAddress("127.0.0.1")
                .loginTime(LocalDateTime.now())
                .build();

        List<LoginHistory> loginHistories = List.of(loginHistory);

        // loginHistoryRepository.findByUserId() 호출 시 loginHistories 반환
        given(loginHistoryRepository.findByUserId(userId)).willReturn(loginHistories);

        // when
        List<AdminLoginHistoryResponseDto> result = adminLoginHistoryService.getLoginHistoryByUserId(userId);

        // then
        assertEquals(1, result.size()); // 반환된 기록 개수가 1인지 검증
        assertEquals("TestUser", result.get(0).getNickname()); // 닉네임이 "TestUser"인지 검증
        verify(loginHistoryRepository, times(1)).findByUserId(userId); // 호출 횟수 검증
    }

    @Test
    void 로그인_기록_저장_테스트() {
        // given
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .nickname("TestUser")
                .build();

        given(userRepository.findById(userId)).willReturn(Optional.of(user)); // userRepository에서 유효한 User 반환

        // when
        adminLoginHistoryService.saveLoginHistory(userId, "127.0.0.1");

        // then
        ArgumentCaptor<LoginHistory> captor = ArgumentCaptor.forClass(LoginHistory.class);
        verify(loginHistoryRepository, times(1)).save(captor.capture());

        LoginHistory capturedLoginHistory = captor.getValue();
        assertEquals(user, capturedLoginHistory.getUser());
        assertEquals("127.0.0.1", capturedLoginHistory.getIpAddress());
    }

    @Test
    void 로그인_기록_저장_실패_테스트_사용자_없음() {
        // given
        Long userId = 1L;
        given(userRepository.findById(userId)).willReturn(Optional.empty()); // 존재하지 않는 사용자 반환

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                adminLoginHistoryService.saveLoginHistory(userId, "127.0.0.1"));
        assertEquals("해당 ID의 사용자를 찾을 수 없습니다: " + userId, exception.getMessage());
        verify(userRepository, times(1)).findById(userId); // 호출 검증
    }
}
