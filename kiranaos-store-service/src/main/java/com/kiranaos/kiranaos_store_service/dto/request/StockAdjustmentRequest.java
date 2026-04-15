package com.kiranaos.kiranaos_store_service.dto.request;

import com.kiranaos.kiranaos_store_service.domain.enums.AdjustmentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockAdjustmentRequest {
    @NotNull
    @Positive
    private BigDecimal quantityChange;
    @NotNull
    private AdjustmentType adjustmentType;
    private String reason;
}
