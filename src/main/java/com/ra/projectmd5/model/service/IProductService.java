package com.ra.projectmd5.model.service;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.ProductRequest;
import com.ra.projectmd5.model.dto.request.ProductUpdateRequest;
import com.ra.projectmd5.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<Product> findAll(Pageable pageable, String search);
    Product getProductById(Long id);
    Product saveProduct(ProductRequest productRequest) throws DataExistException;
    boolean existsByProductName(String productName);
    Product updateProduct(Long id, ProductUpdateRequest productRequest) throws DataExistException;
    void deleteProduct(Long id);

    Page<Product> findAllWithFilter(Pageable pageable, String search, Double minPrice, Double maxPrice, Long colorId, String sortOption);
    Page<Product> findAllByProductNameContainsIgnoreCase(String productName, Pageable pageable);
    Page<Product> findAllProductsByNameAndSort(String productName, Pageable pageable, String sortOption);

    Page<Product> findAll(Pageable pageable, String search, Double minPrice, Double maxPrice, String color, String sortOption);
    Product changeStatus(Long id);

}
