package com.kiranaos.kiranaos_store_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateStoreRequest {
    @NotBlank
    private String name;
    private String address;
    private String phone;
    private String gstNumber;

}
