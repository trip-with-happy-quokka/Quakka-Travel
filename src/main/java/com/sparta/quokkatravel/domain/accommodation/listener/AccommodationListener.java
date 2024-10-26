package com.sparta.quokkatravel.domain.accommodation.listener;

import com.sparta.quokkatravel.domain.accommodation.entity.Accommodation;
import com.sparta.quokkatravel.domain.review.entity.Review;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.web.bind.annotation.PostMapping;

public class AccommodationListener {

    @PostPersist
    @PostUpdate
    @PostRemove
    public void onReviewSaveOrUpdateOrDelete(Review review) {
        Accommodation accommodation = review.getAccommodation();
        accommodation.updateRating();
    }
}
