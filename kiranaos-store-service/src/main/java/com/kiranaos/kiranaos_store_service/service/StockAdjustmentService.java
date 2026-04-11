package com.kiranaos.kiranaos_store_service.service;

import com.kiranaos.kiranaos_store_service.domain.Product;
import com.kiranaos.kiranaos_store_service.domain.StockAdjustment;
import com.kiranaos.kiranaos_store_service.domain.Store;
import com.kiranaos.kiranaos_store_service.domain.enums.AdjustmentType;
import com.kiranaos.kiranaos_store_service.dto.request.StockAdjustmentRequest;
import com.kiranaos.kiranaos_store_service.dto.response.StockAdjustmentResponse;
import com.kiranaos.kiranaos_store_service.exception.InsufficientStockException;
import com.kiranaos.kiranaos_store_service.repository.StockAdjustmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockAdjustmentService {
    private final StockAdjustmentRepository stockAdjustmentRepository;
    private final StoreService storeService;
    private final ProductService productService;

    @Transactional
    public StockAdjustmentResponse adjustStock(UUID ownerId, UUID productId, StockAdjustmentRequest request) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        Product product = productService.findByProductId(productId, store.getId());

        if (request.getAdjustmentType() == AdjustmentType.SALE) {
            if (product.getStockQuantity().compareTo(request.getQuantityChange()) < 0) {
                throw new InsufficientStockException("Insufficient stock for this product");
            }
            product.setStockQuantity(product.getStockQuantity().subtract(request.getQuantityChange()));
        } else {
            product.setStockQuantity(product.getStockQuantity().add(request.getQuantityChange()));
        }
        productService.saveProduct(product);

        StockAdjustment saved = stockAdjustmentRepository.save(
                StockAdjustment.builder()
                        .storeId(store.getId())
                        .product(product)
                        .adjustmentType(request.getAdjustmentType())
                        .quantityChange(request.getQuantityChange())
                        .reason(request.getReason())
                        .build());

        return toStockAdjustmentResponse(saved);
    }

    private StockAdjustmentResponse toStockAdjustmentResponse(StockAdjustment stockAdjustment) {
        return StockAdjustmentResponse.builder()
                .id(stockAdjustment.getId())
                .storeId(stockAdjustment.getStoreId())
                .productId(stockAdjustment.getProduct().getId())
                .productName(stockAdjustment.getProduct().getName())
                .adjustmentType(stockAdjustment.getAdjustmentType())
                .reason(stockAdjustment.getReason())
                .quantityChange(stockAdjustment.getQuantityChange())
                .createdAt(stockAdjustment.getCreatedAt())
                .build();
    }
}
