package com.kiranaos.kiranaos_store_service.dto.response;

import com.kiranaos.kiranaos_store_service.domain.enums.AdjustmentType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class StockAdjustmentResponse {
    private UUID id;
    private UUID storeId;
    private UUID productId;
    private String productName;
    private AdjustmentType adjustmentType;
    private BigDecimal quantityChange;
    private String reason;
    private Instant createdAt;
}
