package com.sparta.quokkatravel.domain.admin.useractivity.dto;

import com.sparta.quokkatravel.domain.admin.useractivity.entity.UserActivity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminUserActivityResponseDto {

    private Long activityId;
    private Long userId;
    private String activityType;
    private LocalDateTime activityDate;
    private String description;

    // UserActivity 엔티티로부터 DTO 생성
    public AdminUserActivityResponseDto(UserActivity activity) {
        this.activityId = activity.getId();
        this.userId = activity.getUser().getId();
        this.activityType = activity.getActivityType();
        this.activityDate = activity.getActivityDate();
        this.description = activity.getDescription();
    }
}