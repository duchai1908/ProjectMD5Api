package com.ra.projectmd5.model.service;

import com.ra.projectmd5.model.entity.ImageProductDetail;

import java.util.List;

public interface IImageProductDetailService {
    ImageProductDetail save(ImageProductDetail imageProductDetail);
    void deleteImageByProductDetailId(Long productDetailId);
    List<ImageProductDetail> findImageByProductDetailId(Long productDetailId);
}
