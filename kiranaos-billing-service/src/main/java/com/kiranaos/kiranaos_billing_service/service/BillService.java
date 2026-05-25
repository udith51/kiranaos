package com.kiranaos.kiranaos_billing_service.service;

import com.kiranaos.kiranaos_billing_service.client.StoreServiceClient;
import com.kiranaos.kiranaos_billing_service.domain.Bill;
import com.kiranaos.kiranaos_billing_service.domain.BillItem;
import com.kiranaos.kiranaos_billing_service.dto.request.BillItemRequest;
import com.kiranaos.kiranaos_billing_service.dto.request.CreateBillRequest;
import com.kiranaos.kiranaos_billing_service.dto.response.BillResponse;
import com.kiranaos.kiranaos_billing_service.dto.response.BillSummaryResponse;
import com.kiranaos.kiranaos_billing_service.dto.response.StoreResponse;
import com.kiranaos.kiranaos_billing_service.dto.response.ProductResponse;
import com.kiranaos.kiranaos_billing_service.dto.response.BillItemResponse;
import com.kiranaos.kiranaos_billing_service.exception.AccessDeniedException;
import com.kiranaos.kiranaos_billing_service.exception.BillNotFoundException;
import com.kiranaos.kiranaos_billing_service.exception.StoreNotFoundException;
import com.kiranaos.kiranaos_billing_service.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillService {
    public static final int VAL = 100;
    private final BillRepository billRepository;
    private final StoreServiceClient storeServiceClient;

    public Page<BillSummaryResponse> getBills(UUID ownerId, int page, int size) {
        StoreResponse store = storeServiceClient.getStore(ownerId);
        if (store == null) {
            throw new StoreNotFoundException("Store not found");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Bill> bills = billRepository.findAllByStoreId(store.getId(), pageable);
        return bills.map(this::toBillSummaryResponse);
    }

    public BillResponse getBillById(UUID ownerId, UUID billId) {
        StoreResponse store = storeServiceClient.getStore(ownerId);
        if (store == null) {
            throw new StoreNotFoundException("Store not found");
        }
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new BillNotFoundException("No such bill exists"));

        if (!bill.getStoreId().equals(store.getId())) {
            throw new AccessDeniedException("Access denied");
        }
        return toBillResponse(bill);
    }

    @Transactional
    public BillResponse createBill(CreateBillRequest createBillRequest, UUID ownerId) {
        StoreResponse store = storeServiceClient.getStore(ownerId);
        if (!store.getId().equals(createBillRequest.getStoreId()))
            throw new StoreNotFoundException("No such store exists");

        int billNumber = billRepository.countByStoreId(createBillRequest.getStoreId()) + 1;

        Bill bill = new Bill();
        bill.setStoreId(createBillRequest.getStoreId());
        bill.setBillNumber(billNumber);
        bill.setCustomerName(createBillRequest.getCustomerName());
        bill.setCustomerPhone(createBillRequest.getCustomerPhone());

        List<BillItem> billItems = createBillRequest.getItems().stream().map(item -> toBillItem(item, bill, ownerId)).toList();
        bill.setItems(billItems);

        BigDecimal subTotal = billItems.stream().map(item -> item.getUnitPrice().multiply(item.getQuantity())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal gstTotal = billItems.stream().map(item -> item.getQuantity().multiply(item.getUnitPrice()).multiply(item.getGstRate().divide(BigDecimal.valueOf(VAL), 2, RoundingMode.HALF_UP))).reduce(BigDecimal.ZERO, BigDecimal::add);
        bill.setSubTotal(subTotal);
        bill.setGstTotal(gstTotal);
        bill.setGrandTotal(subTotal.add(gstTotal));
        return toBillResponse(billRepository.save(bill));
    }

    private BillItem toBillItem(BillItemRequest request, Bill bill, UUID ownerId) {
        ProductResponse response = storeServiceClient.getProductById(request.getProductId(), ownerId);
        BillItem item = new BillItem();
        item.setBill(bill);
        item.setProductId(request.getProductId());
        item.setProductName(response.getName());
        item.setQuantity(request.getQuantity());
        item.setUnitType(response.getUnit());
        item.setUnitPrice(response.getPrice());
        item.setGstRate(response.getGstRate());
        item.setItemTotal(calculateTotalAmt(request.getQuantity(), response.getPrice(), response.getGstRate()));
        return item;
    }

    private BigDecimal calculateTotalAmt(BigDecimal quantity, BigDecimal unitPrice, BigDecimal gstRate) {
        BigDecimal gstMultiplier = BigDecimal.ONE.add(gstRate.divide(BigDecimal.valueOf(VAL), 2, RoundingMode.HALF_UP));
        return quantity.multiply(unitPrice).multiply(gstMultiplier);
    }

    private BillResponse toBillResponse(Bill bill) {
        BillResponse response = new BillResponse();
        response.setId(bill.getId());
        response.setStoreId(bill.getStoreId());
        response.setBillNumber(bill.getBillNumber());
        response.setCustomerName(bill.getCustomerName());
        response.setCustomerPhone(bill.getCustomerPhone());
        response.setItems(bill.getItems().stream().map(this::toBillItemResponse).toList());
        response.setSubTotal(bill.getSubTotal());
        response.setGstTotal(bill.getGstTotal());
        response.setGrandTotal(bill.getGrandTotal());
        response.setPdfUrl(bill.getPdfUrl());
        response.setWhatsappStatus(bill.getWhatsappStatus());
        response.setCreatedAt(bill.getCreatedAt());
        return response;
    }

    private BillItemResponse toBillItemResponse(BillItem billItem) {
        return BillItemResponse.builder().id(billItem.getId()).productId(billItem.getProductId()).productName(billItem.getProductName()).quantity(billItem.getQuantity()).unitType(billItem.getUnitType()).unitPrice(billItem.getUnitPrice()).gstRate(billItem.getGstRate()).itemTotal(billItem.getItemTotal()).build();
    }

    private BillSummaryResponse toBillSummaryResponse(Bill bill) {
        return BillSummaryResponse.builder().id(bill.getId()).billNumber(bill.getBillNumber()).customerName(bill.getCustomerName()).customerPhone(bill.getCustomerPhone()).subTotal(bill.getSubTotal()).gstTotal(bill.getGstTotal()).grandTotal(bill.getGrandTotal()).whatsappStatus(bill.getWhatsappStatus()).createdAt(bill.getCreatedAt()).build();
    }
}
