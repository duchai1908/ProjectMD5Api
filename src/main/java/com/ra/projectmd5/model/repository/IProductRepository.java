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
            "(:colorId IS NULL OR pd.color.color = :colorId)" +
            "ORDER BY " +
            "CASE WHEN :sortOption = 'lowToHigh' THEN pd.price END ASC, " +
            "CASE WHEN :sortOption = 'highToLow' THEN pd.price END DESC")
    Page<Product> findProductsWithFilters(Pageable pageable,
                                          @Param("search") String search,
                                          @Param("minPrice") Double minPrice,
                                          @Param("maxPrice") Double maxPrice,
                                          @Param("colorId") Long colorId,
                                          @Param("sortOption") String sortOption);
    Page<Product> findProductsByNameContainsIgnoreCaseOrderByNameAsc(String name, Pageable pageable);
    Page<Product> findProductsByNameContainsIgnoreCaseOrderByNameDesc(String name, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseOrderByNameAsc(String search, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseOrderByNameDesc(String search, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseOrderByUpdatedAtAsc(String search, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseOrderByUpdatedAtDesc(String search, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String search, Pageable pageable);
}
