package com.kiranaos.kiranaos_store_service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ReceiptConfigResponse {
    private String receiptHeader;
    private String receiptFooter;
    private BigDecimal defaultGstRate;
    private Boolean showGstBreakdown;
    private Boolean showGstNumber;
}
