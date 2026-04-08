package com.kiranaos.kiranaos_store_service.repository;

import com.kiranaos.kiranaos_store_service.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByIdAndStore_Id(UUID id, UUID storeId);
    List<Product> findAllByStore_IdAndIsDeletedFalse(UUID storeId);
}
