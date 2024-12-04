package com.example.transaction_service.controller;

import com.example.transaction_service.dto.payment.TransactionDto;
import com.example.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/transaction")
    public TransactionDto transaction(@RequestBody TransactionDto transactionDto){
        return service.transaction(transactionDto);
    }

    @GetMapping("/{uid}/status")
    public TransactionDto transactionStatus(@PathVariable UUID uid){
        return service.transactionStatus(uid);
    }
}
