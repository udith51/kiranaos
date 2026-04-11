package com.kiranaos.kiranaos_store_service.service;

import com.kiranaos.kiranaos_store_service.domain.Product;
import com.kiranaos.kiranaos_store_service.domain.Store;
import com.kiranaos.kiranaos_store_service.dto.request.CreateProductRequest;
import com.kiranaos.kiranaos_store_service.dto.request.UpdateProductRequest;
import com.kiranaos.kiranaos_store_service.dto.response.MessageResponse;
import com.kiranaos.kiranaos_store_service.dto.response.ProductResponse;
import com.kiranaos.kiranaos_store_service.exception.ProductNotFoundException;
import com.kiranaos.kiranaos_store_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreService storeService;

    public ProductResponse createProduct(UUID ownerId, CreateProductRequest request) {
        Store store = storeService.findStoreByOwnerId(ownerId);

        Product product = new Product();
        product.setName(request.getName());
        product.setStore(store);
        product.setCategory(request.getCategory());
        product.setUnit(request.getUnit());
        product.setPrice(request.getPrice());
        product.setGstRate(request.getGstRate());
        product.setStockQuantity(request.getStockQuantity());
        product.setReorderThreshold(request.getReorderThreshold());
        return toProductResponse(productRepository.save(product));
    }

    public ProductResponse getProduct(UUID productId, UUID ownerId) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        Product product = productRepository.findByIdAndStoreId(productId, store.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return toProductResponse(product);
    }

    public List<ProductResponse> getProducts(UUID ownerId) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        List<Product> products = productRepository.findAllByStoreIdAndIsDeletedFalse(store.getId());
        return products.stream().map(this::toProductResponse).toList();
    }

    public ProductResponse updateProduct(UUID productId, UUID ownerId, UpdateProductRequest request) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        Product product = productRepository.findByIdAndStoreId(productId, store.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getCategory() != null) {
            product.setCategory(request.getCategory());
        }
        if (request.getUnit() != null) {
            product.setUnit(request.getUnit());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getGstRate() != null) {
            product.setGstRate(request.getGstRate());
        }
        if (request.getStockQuantity() != null) {
            product.setStockQuantity(request.getStockQuantity());
        }
        if (request.getReorderThreshold() != null) {
            product.setReorderThreshold(request.getReorderThreshold());
        }
        return toProductResponse(productRepository.save(product));
    }

    public MessageResponse deleteProduct(UUID productId, UUID ownerId) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        Product product = productRepository.findByIdAndStoreId(productId, store.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setIsDeleted(true);
        productRepository.save(product);
        return new MessageResponse("Product deleted!");
    }

    public List<ProductResponse> getProductsByCategory(UUID ownerId, String category) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        List<Product> products = productRepository.findAllByStoreIdAndCategoryAndIsDeletedFalse(store.getId(), category);
        return products.stream().map(this::toProductResponse).toList();
    }

    public List<ProductResponse> findLowStockProducts(UUID ownerId) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        List<Product> products = productRepository.findLowStockProducts(store.getId());
        return products.stream().map(this::toProductResponse).toList();
    }

    public Product findByProductId(UUID productId, UUID storeId) {
        return productRepository.findByIdAndStoreId(productId, storeId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .storeId(product.getStore().getId())
                .name(product.getName())
                .category(product.getCategory())
                .unit(product.getUnit())
                .price(product.getPrice())
                .gstRate(product.getGstRate())
                .stockQuantity(product.getStockQuantity())
                .reorderThreshold(product.getReorderThreshold())
                .build();
    }

}
