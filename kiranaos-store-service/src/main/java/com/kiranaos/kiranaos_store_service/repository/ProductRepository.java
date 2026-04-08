package com.kiranaos.kiranaos_store_service.repository;

import com.kiranaos.kiranaos_store_service.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByIdAndStore__Id(UUID id, UUID storeId);

    List<Product> findAllByStore__IdAndIsDeletedFalse(UUID storeId);

    List<Product> findAllByStore__IdAndCategoryAndIsDeletedFalse(UUID storeId, String category);

    @Query("SELECT p FROM Product p WHERE p.store.id = :storeId AND p.isDeleted = false AND p.stockQuantity<p.reorderThreshold")
    List<Product> findLowStockProducts(@Param("storeId") UUID storeId);
}
