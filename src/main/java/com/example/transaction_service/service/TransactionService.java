package com.example.transaction_service.service;

import com.example.transaction_service.dto.payment.TopUpRequestDto;
import com.example.transaction_service.dto.payment.WithdrawalRequestDto;
import com.example.transaction_service.entity.PaymentRequest;
import com.example.transaction_service.entity.TopUpRequest;
import com.example.transaction_service.entity.Wallet;
import com.example.transaction_service.entity.WithdrawalRequest;
import com.example.transaction_service.mapper.PaymentRequestMapper;
import com.example.transaction_service.mapper.TopUpRequestMapper;
import com.example.transaction_service.mapper.WithdrawalRequestMapper;
import com.example.transaction_service.repository.PaymentRequestRepository;
import com.example.transaction_service.repository.TopUpRequestRepository;
import com.example.transaction_service.repository.WalletRepository;
import com.example.transaction_service.repository.WithdrawalRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final PaymentRequestRepository paymentRequestRepository;
    private final TopUpRequestRepository topUpRequestRepository;
    private final WithdrawalRequestRepository withdrawalRequestRepository;
    private final WalletRepository walletRepository;
    private final PaymentRequestMapper paymentRequestMapper;
    private final TopUpRequestMapper topUpRequestMapper;
    private final WithdrawalRequestMapper withdrawalRequestMapper;

    public TopUpRequestDto topUp(TopUpRequestDto topUpRequestDto) {
        TopUpRequest topUpRequest = topUpRequestMapper.map(topUpRequestDto);
        UUID walletUid = UUID.fromString(topUpRequestDto.getPaymentRequestDto().getWalletUid());
        Wallet wallet = walletRepository.findById(walletUid)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found for ID: " + walletUid));

        PaymentRequest paymentRequest = topUpRequest.getPaymentRequest();
        paymentRequest.setWallet(wallet);
        paymentRequestRepository.save(paymentRequest);
        TopUpRequest savedTopUpRequest = topUpRequestRepository.save(topUpRequest);
        return topUpRequestMapper.map(savedTopUpRequest);
    }

    public WithdrawalRequestDto withdrawal(WithdrawalRequestDto withdrawalRequestDto) {
        WithdrawalRequest withdrawalRequest = withdrawalRequestMapper.map(withdrawalRequestDto);
        UUID walletUid = UUID.fromString(withdrawalRequestDto.getPaymentRequestDto().getWalletUid());
        Wallet wallet = walletRepository.findById(walletUid)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found for ID: " + walletUid));

        PaymentRequest paymentRequest = withdrawalRequest.getPaymentRequest();
        paymentRequest.setWallet(wallet);
        paymentRequestRepository.save(paymentRequest);
        WithdrawalRequest savedWithdrawalRequest = withdrawalRequestRepository.save(withdrawalRequest);
        return withdrawalRequestMapper.map(savedWithdrawalRequest);
    }
}
