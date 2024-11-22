package com.sparta.quokkatravel.domain.review.entity;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.accommodation.listener.AccommodationListener;
import com.sparta.quokkatravel.domain.common.shared.Timestamped;
import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@EntityListeners(AccommodationListener.class)
@NoArgsConstructor
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Long rating;

    private String content;

    public Review(Accommodation accommodation, User user, Long rating, String content) {
        this.accommodation = accommodation;
        this.user = user;
        this.rating = rating;
        this.content = content;
    }

    public void update(Long rating, String content) {
        this.rating = rating;
        this.content = content;
    }

}
