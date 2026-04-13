package com.kiranaos.kiranaos_store_service.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCsvRow {
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "category")
    private String category;
    @CsvBindByName(column = "unit")
    private String unit;
    @CsvBindByName(column = "price")
    private BigDecimal price;
    @CsvBindByName(column = "gstRate")
    private BigDecimal gstRate;
    @CsvBindByName(column = "stockQuantity")
    private BigDecimal stockQuantity;
    @CsvBindByName(column = "reorderThreshold")
    private BigDecimal reorderThreshold;
}
