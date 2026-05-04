package com.kiranaos.kiranaos_billing_service.client;

import com.kiranaos.kiranaos_billing_service.dto.response.ProductResponse;
import com.kiranaos.kiranaos_billing_service.dto.response.StoreResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "kiranaos-store-service")
public interface StoreServiceClient {
    @GetMapping("/api/v1/product/{productId}")
    ProductResponse getProductById(@PathVariable UUID productId, @RequestHeader("X-Owner-Id") UUID ownerId);

    @GetMapping("/api/v1/store/profile")
    StoreResponse getStore(@RequestHeader("X-Owner-Id") UUID ownerId);
}
