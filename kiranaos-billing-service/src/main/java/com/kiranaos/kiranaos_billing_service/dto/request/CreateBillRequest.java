package com.kiranaos.kiranaos_billing_service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBillRequest {
    @NotNull
    private UUID storeId;

    @NotNull
    @NotEmpty
    private List<BillItemRequest> items;

    private String customerName;

    private String customerPhone;
}
