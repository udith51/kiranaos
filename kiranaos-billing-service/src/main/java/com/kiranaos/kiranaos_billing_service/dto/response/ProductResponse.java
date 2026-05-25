package com.kiranaos.kiranaos_billing_service.dto.response;

import com.kiranaos.kiranaos_billing_service.domain.enums.UnitType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private UUID id;
    private String name;
    private UnitType unit;
    private BigDecimal price;
    private BigDecimal gstRate;
    private BigDecimal stockQuantity;
}
