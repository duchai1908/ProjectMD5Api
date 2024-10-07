package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.model.entity.ImageProductDetail;
import com.ra.projectmd5.model.repository.IImageProductDetailRepository;
import com.ra.projectmd5.model.service.IImageProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageProductDetailServiceService implements IImageProductDetailService {
    private final IImageProductDetailRepository imageProductDetailRepository;
    @Override
    public ImageProductDetail save(ImageProductDetail imageProductDetail) {
        return imageProductDetailRepository.save(imageProductDetail);
    }

    @Override
    public void deleteImageByProductDetailId(Long productDetailId) {
        imageProductDetailRepository.deleteImageProductDetailByProductDetailId(productDetailId);
    }

    @Override
    public List<ImageProductDetail> findImageByProductDetailId(Long productDetailId) {
        return imageProductDetailRepository.findImageProductDetailByProductDetailId(productDetailId);
    }
}

