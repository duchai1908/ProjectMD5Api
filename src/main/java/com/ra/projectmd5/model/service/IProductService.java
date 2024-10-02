package com.ra.projectmd5.model.service;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.ProductRequest;
import com.ra.projectmd5.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<Product> findAll(Pageable pageable, String search);
    Product getProductById(Long id);
    Product saveProduct(ProductRequest productRequest) throws DataExistException;
    boolean existsByProductName(String productName);
    Product updateProduct(Long id, ProductRequest productRequest) throws DataExistException;
    void deleteProduct(Long id);

}
