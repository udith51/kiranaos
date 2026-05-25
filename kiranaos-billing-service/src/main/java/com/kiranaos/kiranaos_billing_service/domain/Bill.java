package com.kiranaos.kiranaos_billing_service.domain;

import com.kiranaos.kiranaos_billing_service.domain.enums.WhatsappStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bills")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID storeId;

    @Column(nullable = false)
    private Integer billNumber;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bill", orphanRemoval = true)
    private List<BillItem> items = new ArrayList<>();

    private String customerName;

    private String customerPhone;

    @Column(nullable = false)
    private BigDecimal subTotal;

    @Column(nullable = false)
    private BigDecimal gstTotal;

    @Column(nullable = false)
    private BigDecimal grandTotal;

    private String pdfUrl;

    @Enumerated(EnumType.STRING)
    private WhatsappStatus whatsappStatus = WhatsappStatus.NOT_REQUESTED;

    @CreationTimestamp
    private Instant createdAt;
}
