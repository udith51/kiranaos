package com.kiranaos.kiranaos_billing_service.dto.response;

import com.kiranaos.kiranaos_billing_service.domain.enums.WhatsappStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillSummaryResponse {
    private UUID id;
    private Integer billNumber;
    private String customerName;
    private String customerPhone;
    private BigDecimal subTotal;
    private BigDecimal gstTotal;
    private BigDecimal grandTotal;
    private WhatsappStatus whatsappStatus;
    private Instant createdAt;
}
