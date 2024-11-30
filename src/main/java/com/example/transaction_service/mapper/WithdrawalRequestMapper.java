package com.example.transaction_service.mapper;

import com.example.transaction_service.dto.payment.PaymentRequestDto;
import com.example.transaction_service.dto.payment.WithdrawalRequestDto;
import com.example.transaction_service.entity.PaymentRequest;
import com.example.transaction_service.entity.WithdrawalRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WithdrawalRequestMapper {

    @InheritInverseConfiguration
    @Mapping(target = "paymentRequest", source = "paymentRequestDto")
    WithdrawalRequest map(WithdrawalRequestDto withdrawalRequestDto);
    @Mapping(target = "paymentRequestDto", source = "paymentRequest")
    WithdrawalRequestDto map(WithdrawalRequest withdrawalRequest);


    PaymentRequest map(PaymentRequestDto paymentRequestDto);
    @Mapping(target = "walletUid", source = "wallet.uid")
    PaymentRequestDto map(PaymentRequest paymentRequest);
}
