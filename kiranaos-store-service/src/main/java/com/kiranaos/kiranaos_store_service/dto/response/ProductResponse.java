package com.kiranaos.kiranaos_store_service.dto.response;

import com.kiranaos.kiranaos_store_service.domain.enums.UnitType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class ProductResponse {
    private UUID id;
    private UUID storeId;
    private String name;
    private String category;
    private UnitType unit;
    private BigDecimal price;
    private BigDecimal gstRate;
    private BigDecimal stockQuantity;
    private BigDecimal reorderThreshold;
}
