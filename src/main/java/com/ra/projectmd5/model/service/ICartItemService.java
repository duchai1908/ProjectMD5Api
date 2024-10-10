package com.ra.projectmd5.model.service;

import com.ra.projectmd5.exception.OutOfStockException;
import com.ra.projectmd5.model.dto.request.CartItemRequest;
import com.ra.projectmd5.model.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICartItemService {

    CartItem addToCart(CartItemRequest cartItemRequest, Long userId) throws OutOfStockException;
    List<CartItem> getAllCartItemsNoPagination(Long userId);
    Page<CartItem> getAllCartItems(Pageable pageable, Long userId);
    CartItem changeQuantity(Long cartItemId, Integer quantity);
    void deleteCartItem(Long cartItemId);
    void deleteAllCartItems(Long userId);
    CartItem getCartItem(Long cartItemId);
}
