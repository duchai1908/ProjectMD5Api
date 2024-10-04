package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.model.dto.request.CartItemRequest;
import com.ra.projectmd5.model.entity.CartItem;
import com.ra.projectmd5.model.repository.ICartItemRepository;
import com.ra.projectmd5.model.service.ICartItemService;
import com.ra.projectmd5.model.service.IProductDetailService;
import com.ra.projectmd5.model.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemService implements ICartItemService {
    private final ICartItemRepository cartItemRepository;
    private final IProductDetailService productDetailService;
    private final IUserService userService;
    @Override
    public CartItem addToCart(CartItemRequest cartItemRequest, Long userId) {
        CartItem cartItem = new CartItem();
        if(cartItemRepository.findCartItemByProductDetailIdAndUserId(cartItemRequest.getProductDetailId(), userId) != null) {
            cartItem = cartItemRepository.findCartItemByProductDetailIdAndUserId(cartItemRequest.getProductDetailId(), userId);
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
        }else{
            cartItem = CartItem.builder()
                    .user(userService.findById(userId))
                    .productDetail(productDetailService.getProductDetailById(cartItemRequest.getProductDetailId()))
                    .quantity(cartItemRequest.getQuantity())
                    .build();
        }
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem getCartItem(Long cartItemId) {
        CartItem cartItem = new CartItem();
        cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()->new NoSuchElementException("Không tìm thấy giỏ hàng "));
        return cartItem;
    }

    @Override
    public List<CartItem> getAllCartItemsNoPagination(Long userId) {
        return cartItemRepository.findAllByUserId(userId);
    }

    @Override
    public Page<CartItem> getAllCartItems(Pageable pageable, Long userId) {
        return cartItemRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public CartItem changeQuantity(Long cartItemId, Integer quantity) {
       CartItem cartItem = getCartItem(cartItemId);
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = getCartItem(cartItemId);
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void deleteAllCartItems(Long userId) {
        cartItemRepository.deleteAllByUserId(userId);
    }
}
