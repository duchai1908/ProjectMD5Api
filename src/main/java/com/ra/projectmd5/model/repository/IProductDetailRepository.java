package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Color;
import com.ra.projectmd5.model.entity.Product;
import com.ra.projectmd5.model.entity.ProductDetail;
import com.ra.projectmd5.model.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    Page<ProductDetail> findAllByProductId(Long productId, Pageable pageable);
    Page<ProductDetail> findAllByProductIdContainsIgnoreCase(Long productId,String search, Pageable pageable);
    boolean existsByName(String name);
    boolean existsByColorIdAndSizeIdAndProductId(Long colorId, Long sizeId, Long productId);
    List<ProductDetail> findProductDetailsByProduct_Id(Long productId);
    ProductDetail findByColorAndSizeAndProduct(Color color, Size size, Product product);
    boolean existsByColorId(Long colorId);
    boolean existsBySizeId(Long sizeId);
    //add
}
