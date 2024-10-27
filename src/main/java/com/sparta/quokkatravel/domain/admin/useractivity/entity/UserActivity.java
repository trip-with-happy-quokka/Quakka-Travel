package com.sparta.quokkatravel.domain.admin.useractivity.entity;

import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "User_Activity")
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "activity_type", nullable = false)
    private String activityType;

    @Column(name = "activity_date", nullable = false)
    private LocalDateTime activityDate;

    @Column(name = "description", nullable = true)
    private String description;

    public UserActivity(User user, String activityType, LocalDateTime activityDate, String description) {
        this.user = user;
        this.activityType = activityType;
        this.activityDate = activityDate;
        this.description = description;
    }
}