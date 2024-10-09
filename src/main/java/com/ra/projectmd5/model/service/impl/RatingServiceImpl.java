package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.RatingRequest;
import com.ra.projectmd5.model.entity.Rating;
import com.ra.projectmd5.model.entity.User;
import com.ra.projectmd5.model.repository.IRatingRepository;
import com.ra.projectmd5.model.service.IProductDetailService;
import com.ra.projectmd5.model.service.IProductService;
import com.ra.projectmd5.model.service.IRatingService;
import com.ra.projectmd5.model.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements IRatingService {
    private final IRatingRepository ratingRepository;
    private final IUserService userService;
    private final IProductService productService;

    /**
     * @Param pageable Pageable
     * @Param username String
     * @apiNote Lấy ra toàn bộ đánh giá ( Phân trang, sắp xếp, tìm kiếm theo username)
     * Auth: Duc Hai (03/10/2024)
     * */
    @Override
    public Page<Rating> findAllRatings(Pageable pageable, String username) {
        if(username != null && !username.isEmpty()) {
            return ratingRepository.findAllByUserUsernameContains(username, pageable);
        }else{
            return ratingRepository.findAll(pageable);
        }
    }

    @Override
    public Rating addRating(RatingRequest ratingRequest,Long userId) throws DataExistException {
        List<Rating> listRate = ratingRepository.findAll();
        User user = userService.findById(userId);
        boolean check = true;
        if(!listRate.isEmpty()){
            for(Rating rating : listRate){
                if(rating.getUser().getId().equals(userId) && rating.getProducts().getId().equals(ratingRequest.getProductId())){
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
                .products(productService.getProductById(ratingRequest.getProductId()))
                .createdAt(new Date())
                .build();
        return ratingRepository.save(rating);
    }

    @Override
    public Page<Rating> findAllRatingsByProductId(Long productId, Pageable pageable) {
        return ratingRepository.findAllByProductsId(productId, pageable);
    }

    @Override
    public Map<String, Object> findRatingByProductId(Long productId) {
        Object[] averageResult = ratingRepository.findAverageRatingAndCountByProductId(productId);
        List<Object[]> countResults = ratingRepository.findCountByRatingGroupedByProductId(productId);

        Map<String, Object> stats = new HashMap<>();
        stats.put("averageRating", averageResult[0]);

        Map<Integer, Long> ratingCount = new HashMap<>();
        for (Object[] result : countResults) {
            ratingCount.put((Integer) result[0], (Long) result[1]);
        }
        stats.put("ratingCount", ratingCount);
        return stats;
    }

    @Override
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }
}
