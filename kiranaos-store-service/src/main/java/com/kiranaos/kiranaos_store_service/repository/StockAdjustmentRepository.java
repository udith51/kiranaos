package com.kiranaos.kiranaos_store_service.repository;

import com.kiranaos.kiranaos_store_service.domain.StockAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StockAdjustmentRepository extends JpaRepository<StockAdjustment, UUID> {
    List<StockAdjustment> findAllByProductIdOrderByCreatedAtDesc(UUID productId);

    List<StockAdjustment> findAllByStoreIdOrderByCreatedAtDesc(UUID storeId);
}
