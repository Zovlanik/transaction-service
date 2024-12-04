package com.example.transaction_service.service;

import com.example.transaction_service.dto.payment.TopUpRequestDto;
import com.example.transaction_service.dto.payment.TransferRequestDto;
import com.example.transaction_service.dto.payment.WithdrawalRequestDto;
import com.example.transaction_service.entity.*;
import com.example.transaction_service.mapper.TopUpRequestMapper;
import com.example.transaction_service.mapper.TransferRequestMapper;
import com.example.transaction_service.mapper.WithdrawalRequestMapper;
import com.example.transaction_service.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final PaymentRequestRepository paymentRequestRepository;
    private final TopUpRequestRepository topUpRequestRepository;
    private final WithdrawalRequestRepository withdrawalRequestRepository;
    private final TransferRequestRepository transferRequestRepository;
    private final WalletRepository walletRepository;
    private final TransferRequestMapper transferRequestMapper;
    private final TopUpRequestMapper topUpRequestMapper;
    private final WithdrawalRequestMapper withdrawalRequestMapper;

    public TopUpRequestDto topUp(TopUpRequestDto topUpRequestDto) {
        TopUpRequest topUpRequest = topUpRequestMapper.map(topUpRequestDto);
        UUID walletUid = UUID.fromString(topUpRequestDto.getPaymentRequestDto().getWalletUid());
        Wallet wallet = getWallet(walletUid);

        PaymentRequest paymentRequest = topUpRequest.getPaymentRequest();
        paymentRequest.setWallet(wallet);
        paymentRequestRepository.save(paymentRequest);
        TopUpRequest savedTopUpRequest = topUpRequestRepository.save(topUpRequest);
        return topUpRequestMapper.map(savedTopUpRequest);
    }

    public WithdrawalRequestDto withdrawal(WithdrawalRequestDto withdrawalRequestDto) {
        WithdrawalRequest withdrawalRequest = withdrawalRequestMapper.map(withdrawalRequestDto);
        UUID walletUid = UUID.fromString(withdrawalRequestDto.getPaymentRequestDto().getWalletUid());
        Wallet wallet = getWallet(walletUid);

        PaymentRequest paymentRequest = withdrawalRequest.getPaymentRequest();
        paymentRequest.setWallet(wallet);
        paymentRequestRepository.save(paymentRequest);
        WithdrawalRequest savedWithdrawalRequest = withdrawalRequestRepository.save(withdrawalRequest);
        return withdrawalRequestMapper.map(savedWithdrawalRequest);
    }

    public TransferRequestDto transfer(TransferRequestDto transferRequestDto) {
        TransferRequest transferRequest = transferRequestMapper.map(transferRequestDto);
        UUID walletUidTo = UUID.fromString(transferRequestDto.getPaymentRequestDtoTo().getWalletUid());
        Wallet walletTo = getWallet(walletUidTo);
        UUID walletUidFrom = UUID.fromString(transferRequestDto.getPaymentRequestDtoFrom().getWalletUid());
        Wallet walletFrom = getWallet(walletUidFrom);

        PaymentRequest paymentRequestTo = transferRequest.getPaymentRequestTo();
        PaymentRequest paymentRequestFrom = transferRequest.getPaymentRequestFrom();
        paymentRequestTo.setWallet(walletTo);
        paymentRequestFrom.setWallet(walletFrom);
        paymentRequestRepository.save(paymentRequestTo);
        paymentRequestRepository.save(paymentRequestFrom);
        TransferRequest savedTransferRequest = transferRequestRepository.save(transferRequest);
        return transferRequestMapper.map(savedTransferRequest);
    }

    private Wallet getWallet(UUID walletUid) {
        return walletRepository.findById(walletUid)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found for ID: " + walletUid));
    }
}
