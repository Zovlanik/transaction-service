package com.example.transaction_service.controller;

import com.example.transaction_service.dto.wallet.WalletDtoRequest;
import com.example.transaction_service.dto.wallet.WalletDtoResponse;
import com.example.transaction_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService service;

    @GetMapping("/{uid}")
    public WalletDtoResponse getWallet(@PathVariable UUID uid){
        return service.getWallet(uid);
    }

    @PostMapping
    public WalletDtoResponse createWallet(@RequestBody WalletDtoRequest walletDtoRequest){
        return service.createWallet(walletDtoRequest);
    }

    @PutMapping("/{uid}")
    public WalletDtoResponse updateWallet(@PathVariable UUID uid, @RequestBody WalletDtoRequest walletDtoRequest){
        return service.updateWallet(uid, walletDtoRequest);
    }

    @DeleteMapping("/{uid}")
    public void deleteWallet(@PathVariable UUID uid){
        service.deleteWallet(uid);
    }

    @GetMapping("/user/{userUid}")
    public List<WalletDtoResponse> getWalletByUserUid(@PathVariable UUID userUid){
        return service.getWalletByUserUid(userUid);
    }
    @GetMapping("/user/{userUid}/currency/{currency}")
    public List<WalletDtoResponse> getWalletByUserUidAndCurrency(@PathVariable UUID userUid,
                                                                 @PathVariable String currency){
        return service.findAllByUserUidAndCurrency(userUid, currency);
    }
}
