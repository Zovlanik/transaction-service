package com.example.transaction_service.dto.payment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class TopUpRequestDto {
    private UUID uid;
    private String provider;
    private PaymentRequestDto paymentRequestDto;
}
