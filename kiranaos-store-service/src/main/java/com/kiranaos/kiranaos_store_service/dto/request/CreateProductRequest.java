package com.kiranaos.kiranaos_store_service.dto.request;

import com.kiranaos.kiranaos_store_service.domain.enums.UnitType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateProductRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String category;
    @NotBlank
    private UnitType unit;
    @NotNull
    private BigDecimal price;
    private BigDecimal gstRate;
    @NotNull
    private BigDecimal stockQuantity;
    private BigDecimal reorderThreshold;
}
