package com.khalid.ecommerce_system.api.pvt;

import com.khalid.ecommerce_system.constant.Constants;
import com.khalid.ecommerce_system.constant.RestResponseMessage;
import com.khalid.ecommerce_system.constant.RestResponseStatusCode;
import com.khalid.ecommerce_system.exception.ProductServiceException;
import com.khalid.ecommerce_system.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.khalid.ecommerce_system.constant.RestApiResponse.buildResponseWithDetails;

@Slf4j
@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    public static final String API_PATH_ADD_PRODUCT = Constants.ApiPath.PRIVATE_API_PATH + "/product/add";
    public static final String API_PATH_UPDATE_PRODUCT = Constants.ApiPath.PRIVATE_API_PATH + "/product/update";
    public static final String API_PATH_DELETE_PRODUCT = Constants.ApiPath.PRIVATE_API_PATH + "/product/delete/{id}";
    public static final String API_PATH_GET_ALL_PRODUCTS = Constants.ApiPath.PUBLIC_API_PATH + "/product-list";
    public static final String API_PATH_GET_PRODUCT_BY_ID = Constants.ApiPath.PUBLIC_API_PATH + "/product/details";
    public static final String API_PATH_SEARCH_PRODUCT = Constants.ApiPath.PUBLIC_API_PATH + "/product/search";
    private static final String API_PATH_SORT_PRODUCTS = Constants.ApiPath.PUBLIC_API_PATH + "/product/sorted-list";


    private final ProductService productService;

    // DTOs
    @Data
    public static class ProductInput {
        @NotBlank(message = "field is mandatory")
        private String name;

        private String description;

        @NotNull(message = "field is mandatory")
        private BigDecimal price;

        @NotNull(message = "field is mandatory")
        private Integer quantity;

        private String imageUrl;
    }

    @PreAuthorize("hasAuthority(@apiPermission.productCreatePermission.code)")
    @PostMapping(path = API_PATH_ADD_PRODUCT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addProduct(@RequestBody @Valid ProductInput input) throws ProductServiceException {
        ProductService.ProductInputParameter parameter = new ProductService.ProductInputParameter(
                input.getName(),
                input.getDescription(),
                input.getPrice(),
                input.getQuantity(),
                input.getImageUrl()
        );
        ProductService.ProductResponse response = productService.addProduct(parameter);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.CREATE_OK, response);
    }
    @PreAuthorize("hasAuthority(@apiPermission.productUpdatePermission.code)")
    @PutMapping(path = API_PATH_UPDATE_PRODUCT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateProduct(@RequestBody @Valid ProductInput input,
                                                @RequestParam(name = "id") Long productId) throws ProductServiceException {
        ProductService.ProductUpdateInputParameter parameter = new ProductService.ProductUpdateInputParameter(
                productId,
                input.getName(),
                input.getDescription(),
                input.getPrice(),
                input.getQuantity(),
                input.getImageUrl()
        );
        ProductService.ProductResponse response = productService.updateProduct(parameter);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.UPDATE_OK, response);
    }

    @PreAuthorize("hasAuthority(@apiPermission.productDeletePermission.code)")
    @DeleteMapping(path = API_PATH_DELETE_PRODUCT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) throws ProductServiceException {
        ProductService.ProductResponse response = productService.deleteProduct(id);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.DELETE_OK, response);
    }

    @PreAuthorize("hasAuthority(@apiPermission.productListPermission.code)")
    @GetMapping(path = API_PATH_GET_ALL_PRODUCTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllProducts(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "3", required = false) int size
    ) throws ProductServiceException {
        List<ProductService.ProductResponse> products = productService.getAllProducts(page, size);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.FETCH_OK, products);
    }

    @PreAuthorize("hasAuthority(@apiPermission.productDetailsPermission.code)")
    @GetMapping(path = API_PATH_GET_PRODUCT_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getProductById(@RequestParam("id") Long id) throws ProductServiceException {
        ProductService.ProductResponse product = productService.getProductById(id);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.FETCH_OK, product);
    }

    @PreAuthorize("hasAuthority(@apiPermission.productListPermission.code)")
    @GetMapping(path = API_PATH_SEARCH_PRODUCT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchProduct(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "3", required = false) int size

    ) throws ProductServiceException {
        List<ProductService.ProductResponse> products = productService.searchProductsByName(keyword, page, size);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.FETCH_OK, products);
    }

    @PreAuthorize("hasAuthority(@apiPermission.productListPermission.code)")
    @GetMapping(path = API_PATH_SORT_PRODUCTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getSortedProducts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection
    ) throws ProductServiceException {
        List<ProductService.ProductResponse> products = productService.getSortedProducts(page, size, sortBy, sortDirection);
        return buildResponseWithDetails(RestResponseStatusCode.OK_STATUS, RestResponseMessage.FETCH_OK, products);
    }
}
