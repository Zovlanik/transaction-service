package com.example.transaction_service.controller;

import com.example.transaction_service.dto.payment.TopUpRequestDto;
import com.example.transaction_service.dto.payment.TransactionDto;
import com.example.transaction_service.dto.payment.TransferRequestDto;
import com.example.transaction_service.dto.payment.WithdrawalRequestDto;
import com.example.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/topUp")
    public TopUpRequestDto topUp(@RequestBody TopUpRequestDto topUpRequestDto){
        return service.topUp(topUpRequestDto);
    }

    @PostMapping("/withdrawal")
    public WithdrawalRequestDto withdrawal(@RequestBody WithdrawalRequestDto withdrawalRequestDto){
        return service.withdrawal(withdrawalRequestDto);
    }

    @PostMapping("/transfer")
    public TransferRequestDto transfer(@RequestBody TransferRequestDto transferRequestDto){
        return service.transfer(transferRequestDto);
    }

    @PostMapping("/transaction")
    public TransactionDto transaction(@RequestBody TransactionDto transactionDto){
        return service.transaction(transactionDto);
    }

    @GetMapping("/{uid}/status")
    public TransactionDto transactionStatus(@PathVariable UUID uid){
        return service.transactionStatus(uid);
    }
}
