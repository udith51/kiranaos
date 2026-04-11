package com.kiranaos.kiranaos_store_service.controller;

import com.kiranaos.kiranaos_store_service.dto.request.StockAdjustmentRequest;
import com.kiranaos.kiranaos_store_service.dto.response.StockAdjustmentResponse;
import com.kiranaos.kiranaos_store_service.service.StockAdjustmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class StockAdjustmentController {

    private final StockAdjustmentService stockAdjustmentService;

    @PostMapping("/{id}/adjust-stock")
    public ResponseEntity<StockAdjustmentResponse> adjustStock(@RequestHeader("X-Owner-Id") UUID ownerId,
                                                               @PathVariable("id") UUID productId,
                                                               @Valid @RequestBody StockAdjustmentRequest stockAdjustmentRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(stockAdjustmentService.adjustStock(ownerId, productId, stockAdjustmentRequest));
    }
}
