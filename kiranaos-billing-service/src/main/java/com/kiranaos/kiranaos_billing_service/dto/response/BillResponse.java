package com.kiranaos.kiranaos_billing_service.dto.response;

import com.kiranaos.kiranaos_billing_service.domain.enums.WhatsappStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillResponse {
    private UUID id;
    private Integer billNumber;
    private UUID storeId;
    private List<BillItemResponse> items;
    private String customerName;
    private String customerPhone;
    private BigDecimal subTotal;
    private BigDecimal gstTotal;
    private BigDecimal grandTotal;
    private String pdfUrl;
    private WhatsappStatus whatsappStatus;
    private Instant createdAt;
}
