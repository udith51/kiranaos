package com.kiranaos.kiranaos_store_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductImportResponse {
    private int successCount;
    private int failedCount;
    private List<String> failedReasons;
}
