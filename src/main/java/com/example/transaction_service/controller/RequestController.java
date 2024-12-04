package com.example.transaction_service.controller;

import com.example.transaction_service.dto.payment.TopUpRequestDto;
import com.example.transaction_service.dto.payment.TransferRequestDto;
import com.example.transaction_service.dto.payment.WithdrawalRequestDto;
import com.example.transaction_service.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/requests")
public class RequestController {

    private final RequestService service;

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
}
