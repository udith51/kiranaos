package com.kiranaos.kiranaos_store_service.controller;

import com.kiranaos.kiranaos_store_service.dto.request.StockAdjustmentRequest;
import com.kiranaos.kiranaos_store_service.dto.response.StockAdjustmentResponse;
import com.kiranaos.kiranaos_store_service.service.StockAdjustmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/adjustments")
@RequiredArgsConstructor
public class StockAdjustmentController {

    private final StockAdjustmentService stockAdjustmentService;

    @PostMapping("/product/{id}")
    public ResponseEntity<StockAdjustmentResponse> adjustStock(@RequestHeader("X-Owner-Id") UUID ownerId, @PathVariable("id") UUID productId, @Valid @RequestBody StockAdjustmentRequest stockAdjustmentRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(stockAdjustmentService.adjustStock(ownerId, productId, stockAdjustmentRequest));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<StockAdjustmentResponse>> productTrails(@RequestHeader("X-Owner-Id") UUID ownerId, @PathVariable("id") UUID productId) {
        return ResponseEntity.status(HttpStatus.OK).body(stockAdjustmentService.trailForProduct(ownerId, productId));
    }

    @GetMapping("/store")
    public ResponseEntity<List<StockAdjustmentResponse>> storeTrails(@RequestHeader("X-Owner-Id") UUID ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(stockAdjustmentService.trailForStore(ownerId));
    }
}
