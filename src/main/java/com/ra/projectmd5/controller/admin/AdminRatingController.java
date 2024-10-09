package com.ra.projectmd5.controller.admin;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/rating")
@RequiredArgsConstructor
public class AdminRatingController {
    private final IRatingService ratingService;

    @GetMapping
    public ResponseEntity<?> getAllRating(@PageableDefault(page = 0, size = 4, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                          @RequestParam(defaultValue = "") String search,
                                          @RequestParam(defaultValue = "id") String sortField,
                                          @RequestParam(defaultValue = "ASC") String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), direction, sortField);
        return new ResponseEntity<>(new ResponseDtoSuccess<>(ratingService.findAllRatings(pageable, search), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
    @DeleteMapping("/{ratingId}")
    public ResponseEntity<?> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Xoá đánh giá thành công",HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
}
