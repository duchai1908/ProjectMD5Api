package com.ra.projectmd5.model.service;

import com.ra.projectmd5.model.entity.Product;

import java.util.Set;

public interface IWishlistService {
    void addToWishlist(Long productId, Long userId);
    Set<Product> getWishlist(Long userId);
    Set<Product> deleteProductFromWishlist(Long productId, Long userId);
    Set<Product> deleteAllProductsFromWishlist(Long userId);
}
