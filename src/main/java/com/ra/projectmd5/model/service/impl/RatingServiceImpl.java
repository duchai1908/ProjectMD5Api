package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.RatingRequest;
import com.ra.projectmd5.model.entity.Rating;
import com.ra.projectmd5.model.repository.IRatingRepository;
import com.ra.projectmd5.model.service.IProductDetailService;
import com.ra.projectmd5.model.service.IRatingService;
import com.ra.projectmd5.model.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements IRatingService {
    private final IRatingRepository ratingRepository;
    private final IUserService userService;
    private final IProductDetailService productDetailService;

    /**
     * @Param pageable Pageable
     * @Param username String
     * @apiNote Lấy ra toàn bộ đánh giá ( Phân trang, sắp xếp, tìm kiếm theo username)
     * Auth: Duc Hai (03/10/2024)
     * */
    @Override
    public Page<Rating> findAllRatings(Pageable pageable, String username) {
        if(username != null && !username.isEmpty()) {
            return ratingRepository.findAllByUserUsername(username, pageable);
        }else{
            return ratingRepository.findAll(pageable);
        }
    }

    @Override
    public Rating addRating(RatingRequest ratingRequest,Long userId) throws DataExistException {
        List<Rating> listRate = ratingRepository.findAll();
        boolean check = true;
        if(!listRate.isEmpty()){
            for(Rating rating : listRate){
                if(rating.getUser().getId().equals(userId) && rating.getProductDetail().getId().equals(ratingRequest.getProductDetail_id())){
                    check = false;
                    break;
                }
            }
        }

        if(!check){
            throw new DataExistException("Bạn đã đánh giá sản phẩm này rồi","messeage");
        }
        Rating rating = Rating.builder()
                .rating(ratingRequest.getRating())
                .comment(ratingRequest.getComment())
                .user(userService.findById(userId))
                .productDetail(productDetailService.getProductDetailById(ratingRequest.getProductDetail_id()))
                .build();
        return ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }
}
