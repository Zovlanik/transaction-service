package com.example.transaction_service.service;

import com.example.transaction_service.dto.wallet.WalletDtoRequest;
import com.example.transaction_service.dto.wallet.WalletDtoResponse;
import com.example.transaction_service.entity.Wallet;
import com.example.transaction_service.entity.WalletType;
import com.example.transaction_service.mapper.WalletMapper;
import com.example.transaction_service.repository.WalletRepository;
import com.example.transaction_service.repository.WalletTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletMapper mapper;
    private final WalletRepository repository;
    private final WalletTypeRepository walletTypeRepository;

    public WalletDtoResponse createWallet(WalletDtoRequest walletDtoRequest) {
        Wallet wallet = mapper.map(walletDtoRequest);
        UUID walletTypeUid = walletDtoRequest.getWalletTypeUid();
        WalletType walletType = walletTypeRepository.findById(walletTypeUid)
                .orElseThrow(() -> new EntityNotFoundException("WalletType not found for ID: " + walletTypeUid));

        wallet.setWalletType(walletType);
        Wallet savedWallet = repository.save(wallet);
        return mapper.map(savedWallet);
    }

    public WalletDtoResponse getWallet(UUID uid) {
        Wallet wallet = repository.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found for ID: " + uid));
        return mapper.map(wallet);
    }

    public WalletDtoResponse updateWallet(UUID uid, WalletDtoRequest walletDtoRequest) {
        Wallet oldWallet = repository.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found for ID: " + uid));
        mapper.updateWalletFromDto(walletDtoRequest, oldWallet);
        oldWallet.setModifiedAt(LocalDateTime.now());
        Wallet savedWallet = repository.save(oldWallet);
        return mapper.map(savedWallet);
    }

    public void deleteWallet(UUID uid) {
        repository.deleteById(uid);
    }

    public List<WalletDtoResponse> getWalletByUserUid(UUID userUid){
        return repository.findAllByUserUid(userUid).stream()
                .map(mapper::map)
                .toList();
    }

    public List<WalletDtoResponse> findAllByUserUidAndCurrency(UUID userUid, String currency){
        return repository.findAllByUserUidAndCurrency(userUid,currency).stream()
                .map(mapper::map)
                .toList();
    }
}
