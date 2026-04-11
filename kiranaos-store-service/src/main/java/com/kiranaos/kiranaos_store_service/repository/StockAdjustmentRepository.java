package com.kiranaos.kiranaos_store_service.repository;

import com.kiranaos.kiranaos_store_service.domain.StockAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockAdjustmentRepository extends JpaRepository<StockAdjustment, UUID> {
}
