package com.sparta.quokkatravel.domain.accommodation.entity;

import com.sparta.quokkatravel.domain.common.timestamped.Timestamped;
import com.sparta.quokkatravel.domain.review.entity.Review;
import com.sparta.quokkatravel.domain.room.entity.Room;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Accommodation extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String address;

    @Column
    private Long rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;


    public Accommodation(String name, String description, String address, User user) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.user = user;
    }

    public void update(String name, String description, String address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }

    // 리뷰를 바탕으로 한 별점 평균 메서드 필요함

}
