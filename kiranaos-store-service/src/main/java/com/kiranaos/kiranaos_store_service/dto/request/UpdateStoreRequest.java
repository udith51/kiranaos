package com.kiranaos.kiranaos_store_service.dto.request;

import lombok.Getter;

@Getter
public class UpdateStoreRequest {
    private String name;
    private String address;
    private String phone;
    private String gstNumber;
}
