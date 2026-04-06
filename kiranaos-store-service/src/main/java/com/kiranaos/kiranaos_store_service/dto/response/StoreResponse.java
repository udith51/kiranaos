package com.kiranaos.kiranaos_store_service.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class StoreResponse {
    private UUID id;
    private String name;
    private String address;
    private String phone;
    private String gstNumber;
    private String logoUrl;
    private String receiptHeader;
    private String receiptFooter;
    private BigDecimal defaultGstRate;
    private Boolean showGstBreakdown;
    private Boolean showGstNumber;
}
