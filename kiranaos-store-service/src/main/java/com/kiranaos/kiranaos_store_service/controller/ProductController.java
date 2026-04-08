package com.kiranaos.kiranaos_store_service.controller;

import com.kiranaos.kiranaos_store_service.dto.request.CreateProductRequest;
import com.kiranaos.kiranaos_store_service.dto.request.UpdateProductRequest;
import com.kiranaos.kiranaos_store_service.dto.response.MessageResponse;
import com.kiranaos.kiranaos_store_service.dto.response.ProductResponse;
import com.kiranaos.kiranaos_store_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestHeader("X-Owner-Id") UUID ownerId,
                                                         @Valid @RequestBody CreateProductRequest createProductRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(ownerId, createProductRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable UUID id,
                                                      @RequestHeader("X-Owner-Id") UUID ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProduct(id, ownerId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(@RequestHeader("X-Owner-Id") UUID ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts(ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id,
                                                         @RequestHeader("X-Owner-Id") UUID ownerId,
                                                         @RequestBody UpdateProductRequest updateProductRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, ownerId, updateProductRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable UUID id,
                                                         @RequestHeader("X-Owner-Id") UUID ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.deleteProduct(id, ownerId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponse>> getAllProductsByCategory(@PathVariable String category,
                                                                          @RequestHeader("X-Owner-Id") UUID ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByCategory(ownerId, category));
    }
}