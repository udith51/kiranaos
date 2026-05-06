package com.kiranaos.kiranaos_billing_service.controller;

import com.kiranaos.kiranaos_billing_service.dto.request.CreateBillRequest;
import com.kiranaos.kiranaos_billing_service.dto.response.BillResponse;
import com.kiranaos.kiranaos_billing_service.dto.response.BillSummaryResponse;
import com.kiranaos.kiranaos_billing_service.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @PostMapping
    public ResponseEntity<BillResponse> createBill(@RequestHeader("X-Owner-Id")UUID ownerId,
                                                   @Valid @RequestBody CreateBillRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(billService.createBill(request,ownerId));
    }

    @GetMapping
    public ResponseEntity<Page<BillSummaryResponse>> getAllBills(@RequestHeader("X-Owner-Id")UUID ownerId,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.OK).body(billService.getBills(ownerId,page,size));
    }

    @GetMapping("/{billId}")
    public ResponseEntity<BillResponse> getBill(@RequestHeader("X-Owner-Id")UUID ownerId,
                                                @PathVariable("billId") UUID billId){
        return ResponseEntity.status(HttpStatus.OK).body(billService.getBillById(ownerId,billId));
    }
}
