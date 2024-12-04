package com.example.transaction_service.service;

import com.example.transaction_service.dto.payment.TransactionDto;
import com.example.transaction_service.entity.*;
import com.example.transaction_service.handler.FailedTransactionException;
import com.example.transaction_service.mapper.TransactionMapper;
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
    private final TransactionMapper transactionMapper;


    public TransactionDto transaction(TransactionDto transactionDto) {
        TransactionType transactionType = transactionDto.getType();

        return switch (transactionType) {
            case TOP_UP -> applyTopUp(transactionDto);
            case WITHDRAWAL -> applyWithdrawal(transactionDto);
            case TRANSFER -> applyTransfer(transactionDto);
        };
    }

    private TransactionDto applyTopUp(TransactionDto transactionDto){

        UUID paymentRequestUid = UUID.fromString(transactionDto.getPaymentRequestDtoUid());
        TopUpRequest topUpRequest = topUpRequestRepository.findById(paymentRequestUid)
                .orElseThrow(() -> new EntityNotFoundException("PaymentRequest not found for ID: " + paymentRequestUid));
        PaymentRequest paymentRequest = topUpRequest.getPaymentRequest();
        checkTransactionAndWallet(transactionDto,paymentRequest);

        Wallet wallet = paymentRequest.getWallet();
        BigDecimal balance = wallet.getBalance();
        BigDecimal add = balance.add(transactionDto.getAmount());
        wallet.setBalance(add);
        wallet = walletRepository.save(wallet);
        paymentRequest.setStatus(Status.DONE);
        paymentRequestRepository.save(paymentRequest);
        Transaction transaction = transactionMapper.map(transactionDto);
        transaction.setWallet(wallet);
        transaction.setPaymentRequest(paymentRequest);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.map(savedTransaction);
    }

    private TransactionDto applyWithdrawal(TransactionDto transactionDto){

        UUID paymentRequestUid = UUID.fromString(transactionDto.getPaymentRequestDtoUid());
        WithdrawalRequest withdrawalRequest = withdrawalRequestRepository.findById(paymentRequestUid)
                .orElseThrow(() -> new EntityNotFoundException("PaymentRequest not found for ID: " + paymentRequestUid));
        PaymentRequest paymentRequest = withdrawalRequest.getPaymentRequest();
        checkTransactionAndWallet(transactionDto,paymentRequest);

        Wallet wallet = paymentRequest.getWallet();
        BigDecimal balance = wallet.getBalance();
        BigDecimal subtract = balance.subtract(transactionDto.getAmount());
        wallet.setBalance(subtract);
        wallet = walletRepository.save(wallet);
        paymentRequest.setStatus(Status.DONE);
        paymentRequestRepository.save(paymentRequest);
        Transaction transaction = transactionMapper.map(transactionDto);
        transaction.setWallet(wallet);
        transaction.setPaymentRequest(paymentRequest);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.map(savedTransaction);
    }

    private TransactionDto applyTransfer(TransactionDto transactionDto){

        UUID paymentRequestUid = UUID.fromString(transactionDto.getPaymentRequestDtoUid());
        TransferRequest transferRequest = transferRequestRepository.findById(paymentRequestUid)
                .orElseThrow(() -> new EntityNotFoundException("PaymentRequest not found for ID: " + paymentRequestUid));
        PaymentRequest paymentRequestFrom = transferRequest.getPaymentRequestFrom();
        PaymentRequest paymentRequestTo = transferRequest.getPaymentRequestTo();

        Wallet walletFrom = paymentRequestFrom.getWallet();
        Wallet walletTo = paymentRequestTo.getWallet();

        BigDecimal amount = paymentRequestFrom.getAmount();

        walletFrom.setBalance(walletFrom.getBalance().subtract(amount));
        walletTo.setBalance(walletTo.getBalance().add(amount));

        walletRepository.save(walletFrom);
        walletRepository.save(walletTo);

        paymentRequestFrom.setStatus(Status.DONE);
        paymentRequestTo.setStatus(Status.DONE);
        paymentRequestRepository.save(paymentRequestFrom);
        paymentRequestRepository.save(paymentRequestTo);

        Transaction transaction = transactionMapper.map(transactionDto);
        transaction.setWallet(walletFrom);
        transaction.setPaymentRequest(paymentRequestFrom);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.map(savedTransaction);
    }

    private void checkTransactionAndWallet(TransactionDto transactionDto, PaymentRequest paymentRequest){
        if(!transactionDto.getWalletUid().equals(paymentRequest.getWallet().getUid().toString())){
            throw new FailedTransactionException("Wrong wallets.");
        }
        if(transactionDto.getAmount().compareTo(paymentRequest.getAmount()) != 0){
            throw new FailedTransactionException("Wrong amount.");
        }
        if(Status.DONE.equals(paymentRequest.getStatus())){
            throw new FailedTransactionException("Payment already applied.");
        }
    }

    public TransactionDto transactionStatus(UUID uid){
        Transaction transaction = transactionRepository.findById(uid)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found for ID: " + uid));
        return transactionMapper.map(transaction);
    }
}
