package com.example.transaction_service.service;

import com.example.transaction_service.dto.payment.TopUpRequestDto;
import com.example.transaction_service.dto.payment.TransactionDto;
import com.example.transaction_service.dto.payment.TransferRequestDto;
import com.example.transaction_service.dto.payment.WithdrawalRequestDto;
import com.example.transaction_service.entity.*;
import com.example.transaction_service.handler.FailedTransactionException;
import com.example.transaction_service.mapper.TopUpRequestMapper;
import com.example.transaction_service.mapper.TransactionMapper;
import com.example.transaction_service.mapper.TransferRequestMapper;
import com.example.transaction_service.mapper.WithdrawalRequestMapper;
import com.example.transaction_service.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final PaymentRequestRepository paymentRequestRepository;
    private final TopUpRequestRepository topUpRequestRepository;
    private final WithdrawalRequestRepository withdrawalRequestRepository;
    private final TransferRequestRepository transferRequestRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final TransferRequestMapper transferRequestMapper;
    private final TopUpRequestMapper topUpRequestMapper;
    private final WithdrawalRequestMapper withdrawalRequestMapper;
    private final TransactionMapper transactionMapper;

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

    private Wallet getWallet(UUID walletUid) {
        return walletRepository.findById(walletUid)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found for ID: " + walletUid));
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

    public TransactionDto transaction(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.map(transactionDto);
        UUID transactionWalletUid = UUID.fromString(transactionDto.getWalletUid());
        Wallet transactionWallet = getWallet(transactionWalletUid);
        transaction.setWallet(transactionWallet);
        UUID paymentRequestDtoUid = UUID.fromString(transactionDto.getPaymentRequestDtoUid());
        PaymentRequest transactionPaymentRequest = paymentRequestRepository.findById(paymentRequestDtoUid)
                .orElseThrow(() -> new EntityNotFoundException("PaymentRequest not found for ID: " + paymentRequestDtoUid));
        transaction.setPaymentRequest(transactionPaymentRequest);
        return applyTransaction(transaction);
    }

    private TransactionDto applyTransaction(Transaction transaction) {

        // недостаточно денег на другом кошельке для пополнения
        BigDecimal amount = transaction.getAmount(); // сумма для пополнения
        BigDecimal balance = transaction.getPaymentRequest().getWallet().getBalance();
        if(balance.compareTo(amount) < 0){
            throw new FailedTransactionException("Not enough money.");
        } else {
            Wallet walletFrom = transaction.getPaymentRequest().getWallet();
            Wallet walletTo = transaction.getWallet();
            walletFrom.setBalance(walletFrom.getBalance().subtract(amount));
            walletTo.setBalance(walletTo.getBalance().add(amount));
            walletRepository.save(walletFrom);
            walletRepository.save(walletTo);
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        TransactionDto map = transactionMapper.map(savedTransaction);
        return map;
    }

    public TransactionDto transactionStatus(UUID uid){
        Transaction transaction = transactionRepository.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found for ID: " + uid));
        return transactionMapper.map(transaction);
    }
}
