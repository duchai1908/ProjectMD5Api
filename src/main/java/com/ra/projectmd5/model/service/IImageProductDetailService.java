package com.ra.projectmd5.model.service;

import com.ra.projectmd5.model.entity.ImageProductDetail;

public interface IImageProductDetailService {
    ImageProductDetail save(ImageProductDetail imageProductDetail);
    void deleteImageByProductDetailId(Long productDetailId);
}
