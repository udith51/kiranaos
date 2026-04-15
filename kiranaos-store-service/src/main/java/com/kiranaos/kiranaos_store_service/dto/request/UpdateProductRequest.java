package com.kiranaos.kiranaos_store_service.dto.request;

import com.kiranaos.kiranaos_store_service.domain.enums.UnitType;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UpdateProductRequest {
    private String name;
    private String category;
    private UnitType unit;
    private BigDecimal price;
    private BigDecimal gstRate;
    private BigDecimal stockQuantity;
    private BigDecimal reorderThreshold;
}
