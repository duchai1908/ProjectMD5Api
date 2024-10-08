package com.ra.projectmd5.model.service;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.RatingRequest;
import com.ra.projectmd5.model.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRatingService {
    Page<Rating> findAllRatings(Pageable pageable, String username);
    Rating addRating(RatingRequest ratingRequest, Long userId) throws DataExistException;
    void deleteRating(Long id);
    Page<Rating> findAllRatingsByProductId(Long productId, Pageable pageable);
}
