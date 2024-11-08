package com.sparta.quokkatravel.domain.admin.useractivity.service;

import com.sparta.quokkatravel.domain.admin.useractivity.dto.AdminUserActivityResponseDto;
import com.sparta.quokkatravel.domain.admin.useractivity.dto.AdminUserStatusUpdateRequestDto;
import com.sparta.quokkatravel.domain.admin.useractivity.entity.UserActivity;
import com.sparta.quokkatravel.domain.admin.useractivity.repository.AdminUserActivityRepository;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import com.sparta.quokkatravel.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AdminUserActivityServiceTest {

    @Mock
    private AdminUserActivityRepository adminUserActivityRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminUserActivityService adminUserActivityService;

    @Test
    void 특정_사용자_활동_조회_테스트() {
        // given
        Long userId = 1L;
        User user = new User();
        ReflectionTestUtils.setField(user, "id", userId);
        ReflectionTestUtils.setField(user, "email", "dummy@example.com");
        ReflectionTestUtils.setField(user, "password", "password");
        ReflectionTestUtils.setField(user, "nickname", "TestUser");
        ReflectionTestUtils.setField(user, "userRole", UserRole.USER);

        UserActivity userActivity = new UserActivity();
        ReflectionTestUtils.setField(userActivity, "user", user);
        ReflectionTestUtils.setField(userActivity, "activityType", "LOGIN");
        ReflectionTestUtils.setField(userActivity, "activityDate", LocalDateTime.now());
        ReflectionTestUtils.setField(userActivity, "description", "사용자가 로그인했습니다.");

        List<UserActivity> userActivities = List.of(userActivity);

        // adminUserActivityRepository.findByUserId() 호출 시 userActivities 반환
        given(adminUserActivityRepository.findByUserId(userId)).willReturn(userActivities);

        // when
        List<AdminUserActivityResponseDto> result = adminUserActivityService.getUserActivities(userId);

        // then
        assertEquals(1, result.size()); // 반환된 활동 내역의 개수가 1인지 검증
        assertEquals("LOGIN", result.get(0).getActivityType()); // 활동 타입이 "LOGIN"인지 검증
        verify(adminUserActivityRepository, times(1)).findByUserId(userId); // 호출 횟수 검증
    }

    @Test
    void 사용자_상태_업데이트_테스트() {
        // given
        Long userId = 1L;
        AdminUserStatusUpdateRequestDto statusUpdateDto = new AdminUserStatusUpdateRequestDto("ACTIVE");
        User user = new User();
        ReflectionTestUtils.setField(user, "id", userId);
        ReflectionTestUtils.setField(user, "email", "dummy@example.com");
        ReflectionTestUtils.setField(user, "password", "password");
        ReflectionTestUtils.setField(user, "nickname", "TestUser");
        ReflectionTestUtils.setField(user, "userRole", UserRole.USER);
        ReflectionTestUtils.setField(user, "status", "INACTIVE");

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        // when
        adminUserActivityService.updateUserStatus(userId, statusUpdateDto);

        // then
        assertEquals("ACTIVE", user.getStatus()); // 상태가 "ACTIVE"로 업데이트되었는지 검증
        verify(userRepository, times(1)).findById(userId); // 사용자 조회 호출 검증
        verify(userRepository, times(1)).save(user); // 사용자 저장 호출 검증

        ArgumentCaptor<UserActivity> captor = ArgumentCaptor.forClass(UserActivity.class);
        verify(adminUserActivityRepository, times(1)).save(captor.capture()); // save 호출 시 전달된 객체 캡처

        UserActivity capturedActivity = captor.getValue();
        assertEquals("STATUS_CHANGE", capturedActivity.getActivityType()); // 필드 값 검증
        assertEquals("관리자가 상태를 변경하였습니다.", capturedActivity.getDescription());
    }

    @Test
    void 사용자_상태_업데이트_실패_테스트_사용자_없음() {
        // given
        Long userId = 1L;
        AdminUserStatusUpdateRequestDto statusUpdateDto = new AdminUserStatusUpdateRequestDto("INACTIVE");
        given(userRepository.findById(userId)).willReturn(Optional.empty()); // 존재하지 않는 사용자 반환

        // when & then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                adminUserActivityService.updateUserStatus(userId, statusUpdateDto));
        assertEquals("사용자를 찾을 수 없습니다.", exception.getMessage()); // 예외 메시지가 예상대로인지 검증
        verify(userRepository, times(1)).findById(userId); // 호출 검증
    }
}
