package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.model.dto.request.CartItemRequest;
import com.ra.projectmd5.model.entity.CartItem;
import com.ra.projectmd5.model.repository.ICartItemRepository;
import com.ra.projectmd5.model.service.ICartItemService;
import com.ra.projectmd5.model.service.IProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final ICartItemRepository cartItemRepository;
    private final IProductDetailService productDetailService;
    @Override
    public CartItem addToCart(CartItemRequest cartItemRequest, Long userId) {
//        if(cartItemRepository.existsById())
        return null;
    }

    @Override
    public List<CartItem> getAllCartItemsNoPagination(Long userId) {
        return List.of();
    }

    @Override
    public Page<CartItem> getAllCartItems(Pageable pageable, Long userId, String search) {
        return null;
    }

    @Override
    public CartItem changeQuantity(Long userId) {
        return null;
    }
}
