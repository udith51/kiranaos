package com.kiranaos.kiranaos_store_service.dto.response;

import lombok.Builder;
import lombok.Getter;

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
}
