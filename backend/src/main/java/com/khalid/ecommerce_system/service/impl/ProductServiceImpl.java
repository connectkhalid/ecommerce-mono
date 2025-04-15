package com.khalid.ecommerce_system.service.impl;

import com.khalid.ecommerce_system.constant.RestErrorMessageDetail;
import com.khalid.ecommerce_system.constant.RestResponseMessage;
import com.khalid.ecommerce_system.constant.RestResponseStatusCode;
import com.khalid.ecommerce_system.entity.ProductInfo;
import com.khalid.ecommerce_system.exception.ProductServiceException;
import com.khalid.ecommerce_system.repository.ProductInfoRepository;
import com.khalid.ecommerce_system.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {
    private final ProductInfoRepository productInfoRepository;

    @Override
    public ProductResponse addProduct(ProductInputParameter param) throws ProductServiceException {
            Optional<ProductInfo> existingProduct = productInfoRepository.findByName(param.getName());
            if (existingProduct.isPresent()) {
                throw new ProductServiceException(
                        RestResponseMessage.PRODUCT_ALREADY_EXISTS,
                        RestResponseStatusCode.ALREADY_EXISTS_STATUS,
                        RestErrorMessageDetail.PRODUCT_ALREADY_EXISTS_ERROR_MESSAGE
                );
            }
            ProductInfo product = mapToEntity(param);
            ProductInfo saved = productInfoRepository.save(product);
            return mapToResponse(saved);
    }

    @Override
    public ProductResponse updateProduct(ProductUpdateInputParameter param) throws ProductServiceException {
            ProductInfo existing = productInfoRepository.findById(param.getId())
                    .orElseThrow(() -> new ProductServiceException(
                            RestResponseMessage.ERROR_IN_UPDATING_PRODUCT,
                            RestResponseStatusCode.NOT_FOUND_STATUS,
                            RestErrorMessageDetail.PRODUCT_NOT_UPDATED_ERROR_MESSAGE
                    ));
            ProductInfo updated = productInfoRepository.save(updateEntity(existing, param));
            return mapToResponse(updated);
    }

    @Override
    public ProductResponse deleteProduct(Long id) throws ProductServiceException {
            ProductInfo existing = productInfoRepository.findById(id)
                    .orElseThrow(() -> new ProductServiceException(
                            RestResponseMessage.ERROR_IN_DELETING_PRODUCT,
                            RestResponseStatusCode.NOT_FOUND_STATUS,
                            RestErrorMessageDetail.PRODUCT_NOT_FOUND_ERROR_MESSAGE
                    ));
            existing.setDeleteFlg(true);
            productInfoRepository.save(existing);
            return mapToResponse(existing);
    }

    @Override
    public List<ProductResponse> getAllProducts(int page, int size) throws ProductServiceException {
            Pageable pageable = PageRequest.of(page - 1, size); // 0-based index
            Page<ProductInfo> productPage = productInfoRepository.findAllProducts(pageable);

            if (productPage.isEmpty()) {
                throw new ProductServiceException(
                        RestResponseMessage.PRODUCT_NOT_FOUND,
                        RestResponseStatusCode.NOT_FOUND_STATUS,
                        RestErrorMessageDetail.NO_PRODUCT_AVAILABLE_ERROR_MESSAGE
                );
            }
            return productPage.getContent().stream()
                    .map(this::mapToResponse)
                    .toList();
    }

    @Override
    public ProductResponse getProductById(Long id) throws ProductServiceException {
        return mapToResponse(getProductInfoById(id));
    }

    @Override
    public List<ProductResponse> searchProductsByName(String keyword, int page, int size) throws ProductServiceException {
        Pageable pageable = PageRequest.of(page - 1, size); // 0-based index
        Page<ProductInfo> productPage = productInfoRepository.findProductsByKeyword(keyword, pageable);

        if (productPage.isEmpty()) {
            throw new ProductServiceException(
                    RestResponseMessage.PRODUCT_NOT_FOUND,
                    RestResponseStatusCode.NOT_FOUND_STATUS,
                    RestErrorMessageDetail.NO_PRODUCT_FOUND_WITH_KEYWORD_ERROR_MESSAGE
            );
        }
        return productPage.getContent().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getSortedProducts(int page, int size, String sortBy, String sortDirection) throws ProductServiceException {
        Set<String> allowedSortFields = Set.of("name", "price", "qty");
        if (!allowedSortFields.contains(sortBy)) {
            throw new ProductServiceException(
                    RestResponseMessage.INVALID_SORT_FIELD,
                    RestResponseStatusCode.VALIDATION_ERROR_STATUS,
                    RestErrorMessageDetail.INVALID_SORT_FIELD_ERROR_MESSAGE
            );
        }
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));

        Page<ProductInfo> productPage = productInfoRepository.findAllProducts(pageable);
        return productPage.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    //Utility Methods
    private ProductInfo mapToEntity(ProductInputParameter param) {
        ProductInfo product = new ProductInfo();
        product.setName(param.getName());
        product.setDescription(param.getDescription());
        product.setPrice(param.getPrice());
        product.setQty(param.getQty());
        product.setImageUrl(param.getImageUrl());
        product.setCreatedDt(new Timestamp(System.currentTimeMillis()));
        product.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
        product.setDeleteFlg(false);
        return product;
    }

    private ProductResponse mapToResponse(ProductInfo entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .qty(entity.getQty())
                .imageUrl(entity.getImageUrl())
                .createdDt(entity.getCreatedDt())
                .updatedDt(entity.getUpdatedDt())
                .deleteFlg(entity.getDeleteFlg())
                .build();
    }

    private ProductInfo updateEntity(ProductInfo entity, ProductUpdateInputParameter param) {
        entity.setName(param.getName());
        entity.setDescription(param.getDescription());
        entity.setPrice(param.getPrice());
        entity.setQty(param.getQty());
        entity.setImageUrl(param.getImageUrl());
        entity.setUpdatedDt(new Timestamp(System.currentTimeMillis()));
        return entity;
    }
    ProductInfo getProductInfoById(Long id) throws ProductServiceException {
        return productInfoRepository.findById(id)
                .orElseThrow(() -> new ProductServiceException(
                        RestResponseMessage.PRODUCT_NOT_FOUND,
                        RestResponseStatusCode.NOT_FOUND_STATUS,
                        RestErrorMessageDetail.PRODUCT_NOT_FOUND_ERROR_MESSAGE
                ));
    }
}
