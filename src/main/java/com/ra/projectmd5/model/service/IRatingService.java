package com.ra.projectmd5.model.service;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.RatingRequest;
import com.ra.projectmd5.model.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface IRatingService {
    Page<Rating> findAllRatings(Pageable pageable, String username);

    Rating addRating(RatingRequest ratingRequest, Long userId) throws DataExistException;

    void deleteRating(Long id);

    Page<Rating> findAllRatingsByProductId(Long productId, Pageable pageable);
    Map<String, Object> findRatingByProductId(Long productId);
}
