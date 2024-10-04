package com.ra.projectmd5.controller.user;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.RatingRequest;
import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IRatingService;
import com.ra.projectmd5.security.principle.UserDetailCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/rating")
public class UserRatingController {
    private final IRatingService ratingService;
    @PostMapping
    public ResponseEntity<?> postNewRating(@RequestBody RatingRequest ratingRequest, @AuthenticationPrincipal UserDetailCustom userDetailCustom) throws DataExistException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(ratingService.addRating(ratingRequest,userDetailCustom.getUsers().getId()), HttpStatus.CREATED.value(), HttpStatus.CREATED), HttpStatus.CREATED);
    }
}
