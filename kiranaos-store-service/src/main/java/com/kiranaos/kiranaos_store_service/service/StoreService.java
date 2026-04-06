package com.kiranaos.kiranaos_store_service.service;

import com.kiranaos.kiranaos_store_service.domain.Store;
import com.kiranaos.kiranaos_store_service.dto.request.CreateStoreRequest;
import com.kiranaos.kiranaos_store_service.dto.request.UpdateStoreRequest;
import com.kiranaos.kiranaos_store_service.dto.response.StoreResponse;
import com.kiranaos.kiranaos_store_service.exception.StoreAlreadyExistsException;
import com.kiranaos.kiranaos_store_service.exception.StoreNotFoundException;
import com.kiranaos.kiranaos_store_service.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreResponse createStore(UUID id, CreateStoreRequest request) {
        if (storeRepository.existsByOwnerId(id)) {
            throw new StoreAlreadyExistsException("Store already exists for this owner");
        }
        Store store = new Store();
        store.setName(request.getName());
        store.setOwnerId(id);
        store.setAddress(request.getAddress());
        store.setPhone(request.getPhone());
        store.setGstNumber(request.getGstNumber());
        Store saved = storeRepository.save(store);
        return toResponse(saved);
    }

    public StoreResponse getStore(UUID ownerId) {
        Store saved = storeRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new StoreNotFoundException("Store not found for this owner"));
        return toResponse(saved);
    }

    public StoreResponse updateStore(UUID id, UpdateStoreRequest updateStoreRequest) {
        Store stored = storeRepository.findByOwnerId(id)
                .orElseThrow(() -> new StoreNotFoundException("Store not found for this owner"));
        if (updateStoreRequest.getAddress() != null) {
            stored.setAddress(updateStoreRequest.getAddress());
        }
        if (updateStoreRequest.getPhone() != null) {
            stored.setPhone(updateStoreRequest.getPhone());
        }
        if (updateStoreRequest.getGstNumber() != null) {
            stored.setGstNumber(updateStoreRequest.getGstNumber());
        }
        if (updateStoreRequest.getName() != null) {
            stored.setName(updateStoreRequest.getName());
        }
        return toResponse(storeRepository.save(stored));
    }

    private StoreResponse toResponse(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .phone(store.getPhone())
                .gstNumber(store.getGstNumber())
                .logoUrl(store.getLogoUrl())
                .build();
    }
}
