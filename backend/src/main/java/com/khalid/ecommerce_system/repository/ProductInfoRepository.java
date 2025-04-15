package com.khalid.ecommerce_system.repository;

import com.khalid.ecommerce_system.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    @Query(value = "SELECT * FROM product_info WHERE LOWER(name) = LOWER(:name) LIMIT 1", nativeQuery = true)
    Optional<ProductInfo> findByName(@Param("name") String name);

    @Query(value = "SELECT * FROM product_info WHERE delete_flg = false", nativeQuery = true)
    Page<ProductInfo> findAllProducts(Pageable pageable);

    @Query("SELECT p FROM ProductInfo p WHERE " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND p.deleteFlg = false")
    Page<ProductInfo> findProductsByKeyword(@Param("keyword") String keyword, Pageable pageable);
}