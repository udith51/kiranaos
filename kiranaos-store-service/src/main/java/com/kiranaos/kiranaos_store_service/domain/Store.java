package com.kiranaos.kiranaos_store_service.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "stores")
@Getter
@Setter
@NoArgsConstructor
public class Store {
    private static final BigDecimal DEFAULT_GST_RATE = BigDecimal.valueOf(5.00);

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private UUID ownerId;

    @Column(nullable = false)
    private String name;

    private String address;
    private String phone;
    private String gstNumber;
    private String logoUrl;

    private String receiptHeader;
    private String receiptFooter;

    @Column(nullable = false)
    private BigDecimal defaultGstRate = DEFAULT_GST_RATE;

    @Column(nullable = false)
    private Boolean showGstBreakdown = true;

    @Column(nullable = false)
    private Boolean showGstNumber = true;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
