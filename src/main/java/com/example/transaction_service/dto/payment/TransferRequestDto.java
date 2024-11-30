package com.example.transaction_service.dto.payment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class TransferRequestDto {

    private String systemRate;
    private PaymentRequestDto paymentRequestDtoFrom;
    private PaymentRequestDto paymentRequestDtoTo;
}
