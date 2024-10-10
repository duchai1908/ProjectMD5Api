package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.model.entity.Product;
import com.ra.projectmd5.model.entity.User;
import com.ra.projectmd5.model.repository.IUserRepository;
import com.ra.projectmd5.model.service.IProductService;
import com.ra.projectmd5.model.service.IUserService;
import com.ra.projectmd5.model.service.IWishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements IWishlistService {
    private final IProductService productService;
    private final IUserService userService;
    private final IUserRepository userRepository;

    @Override
    public Set<Product> getWishlist(Long userId) {
        User user = userService.findById(userId);
        return user.getProducts();
    }

    @Override
    public void addToWishlist(Long productId,Long userId) {
        User user = userService.findById(userId);
        Product product = productService.getProductById(productId);
        boolean check = user.getProducts().contains(product);
        if (check) {
            user.getProducts().remove(product);
        }else{
            Set<Product> products = user.getProducts();
            products.add(product);

        }
        userRepository.save(user);
    }

    @Override
    public Set<Product> deleteProductFromWishlist(Long productId, Long userId) {
        User user = userService.findById(userId);
        Product product = productService.getProductById(productId);
        Set<Product> products = user.getProducts();
        products.remove(product);
        user.setProducts(products);
        userRepository.save(user);
        return user.getProducts();
    }

    @Override
    public Set<Product> deleteAllProductsFromWishlist(Long userId) {
        User user = userService.findById(userId);
        user.setProducts(null);
        userRepository.save(user);
        return user.getProducts();
    }
}
