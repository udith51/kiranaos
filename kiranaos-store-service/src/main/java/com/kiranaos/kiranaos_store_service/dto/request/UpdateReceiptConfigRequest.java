package com.kiranaos.kiranaos_store_service.dto.request;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UpdateReceiptConfigRequest {
    private String receiptHeader;
    private String receiptFooter;
    private BigDecimal defaultGstRate;
    private Boolean showGstNumber;
    private Boolean showGstBreakdown;
}
