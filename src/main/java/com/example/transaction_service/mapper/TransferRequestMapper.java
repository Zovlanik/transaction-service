package com.example.transaction_service.mapper;

import com.example.transaction_service.dto.payment.PaymentRequestDto;
import com.example.transaction_service.dto.payment.TransferRequestDto;
import com.example.transaction_service.entity.PaymentRequest;
import com.example.transaction_service.entity.TransferRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferRequestMapper {

    @InheritInverseConfiguration
    @Mapping(target = "paymentRequestFrom", source = "paymentRequestDtoFrom")
    @Mapping(target = "paymentRequestTo", source = "paymentRequestDtoTo")
    TransferRequest map(TransferRequestDto topUpRequestDto);
    @Mapping(target = "paymentRequestDtoFrom", source = "paymentRequestFrom")
    @Mapping(target = "paymentRequestDtoTo", source = "paymentRequestTo")
    TransferRequestDto map(TransferRequest topUpRequest);

    PaymentRequest map(PaymentRequestDto paymentRequestDto);
    @Mapping(target = "walletUid", source = "wallet.uid")
    PaymentRequestDto map(PaymentRequest paymentRequest);
}
