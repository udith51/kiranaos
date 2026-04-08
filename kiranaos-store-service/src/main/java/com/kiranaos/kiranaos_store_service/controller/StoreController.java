package com.kiranaos.kiranaos_store_service.controller;

import com.kiranaos.kiranaos_store_service.dto.request.CreateStoreRequest;
import com.kiranaos.kiranaos_store_service.dto.request.UpdateReceiptConfigRequest;
import com.kiranaos.kiranaos_store_service.dto.request.UpdateStoreRequest;
import com.kiranaos.kiranaos_store_service.dto.response.ReceiptConfigResponse;
import com.kiranaos.kiranaos_store_service.dto.response.StoreResponse;
import com.kiranaos.kiranaos_store_service.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/profile")
    public ResponseEntity<StoreResponse> createStore(@RequestHeader("X-Owner-Id") UUID ownerId,
                                                     @Valid @RequestBody CreateStoreRequest createStoreRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.createStore(ownerId, createStoreRequest));
    }

    @GetMapping("/profile")
    public ResponseEntity<StoreResponse> getStore(@RequestHeader("X-Owner-Id") UUID ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(storeService.getStore(ownerId));
    }

    @PutMapping("/profile")
    public ResponseEntity<StoreResponse> updateStore(@RequestHeader("X-Owner-Id") UUID ownerId,
                                                     @RequestBody UpdateStoreRequest updateStoreRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(storeService.updateStore(ownerId, updateStoreRequest));
    }

    @GetMapping("/receipt-config")
    public ResponseEntity<ReceiptConfigResponse> getReceiptConfig(@RequestHeader("X-Owner-Id") UUID ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(storeService.getReceiptConfig(ownerId));
    }

    @PutMapping("/receipt-config")
    public ResponseEntity<ReceiptConfigResponse> updateReceiptConfig(@RequestHeader("X-Owner-Id") UUID id,
                                                                     @RequestBody UpdateReceiptConfigRequest updateReceiptConfigRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(storeService.updateReceiptConfig(id, updateReceiptConfigRequest));
    }

}
