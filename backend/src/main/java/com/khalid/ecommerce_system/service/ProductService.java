package com.khalid.ecommerce_system.service;

import com.khalid.ecommerce_system.exception.ProductServiceException;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface ProductService {

    @Data
    class ProductInputParameter {
        private final String name;
        private final String description;
        private final BigDecimal price;
        private final Integer qty;
        private final String imageUrl;
    }

    @Data
    class ProductUpdateInputParameter {
        private final Long id;
        private final String name;
        private final String description;
        private final BigDecimal price;
        private final Integer qty;
        private final String imageUrl;
    }

    @Data
    @Builder
    class ProductResponse {
        private final Long id;
        private final String name;
        private final String description;
        private final BigDecimal price;
        private final Integer qty;
        private final String imageUrl;
        private final Timestamp createdDt;
        private final Timestamp updatedDt;
        private final Boolean deleteFlg;
    }

    ProductResponse addProduct(ProductInputParameter param) throws ProductServiceException;

    ProductResponse updateProduct(ProductUpdateInputParameter param) throws ProductServiceException;

    ProductResponse deleteProduct(Long id) throws ProductServiceException;

    List<ProductResponse> getAllProducts(int page, int size) throws ProductServiceException;

    ProductResponse getProductById(Long id) throws ProductServiceException;

    List<ProductResponse> searchProductsByName(String keyword, int page, int size) throws ProductServiceException;

    List<ProductResponse> getSortedProducts(int page, int size, String sortBy, String sortDirection) throws ProductServiceException;
}
