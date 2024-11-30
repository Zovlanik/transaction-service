package com.example.transaction_service.mapper;

import com.example.transaction_service.dto.payment.PaymentRequestDto;
import com.example.transaction_service.dto.payment.TopUpRequestDto;
import com.example.transaction_service.entity.PaymentRequest;
import com.example.transaction_service.entity.TopUpRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TopUpRequestMapper {

    @InheritInverseConfiguration
    @Mapping(target = "paymentRequest", source = "paymentRequestDto")
    TopUpRequest map(TopUpRequestDto topUpRequestDto);
    @Mapping(target = "paymentRequestDto", source = "paymentRequest")
    TopUpRequestDto map(TopUpRequest topUpRequest);

    PaymentRequest map(PaymentRequestDto paymentRequestDto);
    @Mapping(target = "walletUid", source = "wallet.uid")
    PaymentRequestDto map(PaymentRequest paymentRequest);
}
