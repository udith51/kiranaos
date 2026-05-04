package com.kiranaos.kiranaos_billing_service.repository;

import com.kiranaos.kiranaos_billing_service.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    int countByStoreId(UUID storeId); // for auto-incrementing bill number

    List<Bill> findByStoreId(UUID storeId); // for bill history
}
