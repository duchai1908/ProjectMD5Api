package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductByNameContainsIgnoreCase(String name, Pageable pageable);
    boolean existsByName(String name);
    //search by filter
    @Query("SELECT p FROM Product p JOIN ProductDetail pd ON p.id = pd.product.id WHERE " +
            "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:minPrice IS NULL OR :maxPrice IS NULL OR pd.price BETWEEN :minPrice AND :maxPrice) AND " +
            "(:color IS NULL OR pd.color.color = :color)" +
            "ORDER BY " +
            "CASE WHEN :sortOption = 'lowToHigh' THEN pd.price END ASC, " +
            "CASE WHEN :sortOption = 'highToLow' THEN pd.price END DESC")
    Page<Product> findProductsWithFilters(Pageable pageable,
                                          @Param("search") String search,
                                          @Param("minPrice") Double minPrice,
                                          @Param("maxPrice") Double maxPrice,
                                          @Param("color") String color,
                                          @Param("sortOption") String sortOption);
}
