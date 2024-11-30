package com.example.transaction_service.mapper;

import com.example.transaction_service.dto.payment.PaymentRequestDto;
import com.example.transaction_service.entity.PaymentRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentRequestMapper {

    @InheritInverseConfiguration
    PaymentRequest map(PaymentRequestDto paymentRequestDto);
    PaymentRequestDto map(PaymentRequest paymentRequest);

}
