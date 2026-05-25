package com.kiranaos.kiranaos_billing_service.repository;

import com.kiranaos.kiranaos_billing_service.domain.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillRepository extends JpaRepository<Bill, UUID> {
    int countByStoreId(UUID storeId); // for auto-incrementing bill number

    Page<Bill> findAllByStoreId(UUID storeId, Pageable pageable);
}
