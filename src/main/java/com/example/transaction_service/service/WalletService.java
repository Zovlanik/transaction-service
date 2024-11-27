package com.example.transaction_service.service;

import com.example.transaction_service.dto.WalletDtoRequest;
import com.example.transaction_service.dto.WalletDtoResponse;
import com.example.transaction_service.entity.Wallet;
import com.example.transaction_service.entity.WalletType;
import com.example.transaction_service.mapper.WalletMapper;
import com.example.transaction_service.repository.WalletRepository;
import com.example.transaction_service.repository.WalletTypeRepository;
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
        WalletType walletType = walletTypeRepository.getById(walletDtoRequest.getWalletTypeUid());

        wallet.setWalletType(walletType);
        Wallet savedWallet = repository.save(wallet);
        return mapper.mapRs(savedWallet);
    }

    public WalletDtoResponse getWallet(UUID uid) {
        Wallet wallet = repository.getById(uid);
        return mapper.mapRs(wallet);
    }

    public WalletDtoResponse updateWallet(UUID uid, WalletDtoRequest walletDtoRequest) {
        Wallet oldWallet = repository.getById(uid);
        mapper.updateWalletFromDto(walletDtoRequest, oldWallet);
        oldWallet.setModifiedAt(LocalDateTime.now());
        Wallet savedWallet = repository.save(oldWallet);
        return mapper.mapRs(savedWallet);
    }

    public void deleteWallet(UUID uid) {
        repository.deleteById(uid);
    }

    public List<WalletDtoResponse> getWalletByUserUid(UUID userUid){
        return repository.findAllByUserUid(userUid).stream()
                .map(mapper::mapRs)
                .toList();
    }

    public List<WalletDtoResponse> findAllByUserUidAndCurrency(UUID userUid, String currency){
        return repository.findAllByUserUidAndCurrency(userUid,currency).stream()
                .map(mapper::mapRs)
                .toList();
    }
}
