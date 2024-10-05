package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.exception.OutOfStockException;
import com.ra.projectmd5.model.dto.request.CartItemRequest;
import com.ra.projectmd5.model.entity.CartItem;
import com.ra.projectmd5.model.entity.Product;
import com.ra.projectmd5.model.entity.ProductDetail;
import com.ra.projectmd5.model.repository.ICartItemRepository;
import com.ra.projectmd5.model.service.ICartItemService;
import com.ra.projectmd5.model.service.IProductDetailService;
import com.ra.projectmd5.model.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
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

    /**
     * @Param cartItemRequest CartItemRequest
     * @Param userId Long
     * @apiNote Thêm sản phẩm vào giỏ hàng
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public CartItem addToCart(CartItemRequest cartItemRequest, Long userId) throws OutOfStockException {
        CartItem cartItem = new CartItem();
        ProductDetail productDetail = productDetailService.getProductDetailById(cartItemRequest.getProductDetailId());
        if(cartItemRequest.getQuantity() > productDetail.getStock()){
            throw new OutOfStockException("Số lượng sản phẩm không được vượt quá số lượng kho","message");
        }
        // Kiểm tra xem có sản phẩm trong giỏ hay chưa, nếu có rồi thì cộng số lượng, chưa có thì thêm mới
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

    /**
     * @Param cartItemId Long
     * @apiNote Lấy thông tin chi tiết 1 sản phẩm trong giỏ hàng
     * @throws NoSuchElementException không tìm thấy giỏ hàng
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public CartItem getCartItem(Long cartItemId) {
        CartItem cartItem = new CartItem();
        cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()->new NoSuchElementException("Không tìm thấy giỏ hàng "));
        return cartItem;
    }

    /**
     * @Param userId Long
     * @apiNote Lấy toàn bộ sản phẩm trong giỏ hàng ( k có phân trang )
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public List<CartItem> getAllCartItemsNoPagination(Long userId) {
        return cartItemRepository.findAllByUserId(userId);
    }

    /**
     * @Param pageable Pageable
     * @Param userId Long
     * @apiNote Lấy toàn bộ sản phẩm trong giỏ hàng ( có phân trang )
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public Page<CartItem> getAllCartItems(Pageable pageable, Long userId) {
        return cartItemRepository.findAllByUserId(userId, pageable);
    }

    /**
     * @Param cartItemId Long
     * @Param quantity Integer
     * @apiNote Thay đổi số lượng 1 sản phẩm trong giỏ hàng
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public CartItem changeQuantity(Long cartItemId, Integer quantity) {
       CartItem cartItem = getCartItem(cartItemId);
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    /**
     * @Param cartItemId Long
     * @apiNote Xoá 1 sản phẩm trong giỏ hàng
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = getCartItem(cartItemId);
        cartItemRepository.delete(cartItem);
    }

    /**
     * @Param userId Long
     * @apiNote Xoá toàn bộ sản phẩm trong giỏ hàng
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public void deleteAllCartItems(Long userId) {
        cartItemRepository.deleteAllByUserId(userId);
    }
}
