package com.example.transaction_service.dto.payment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class TransactionDto {
    private UUID uid;
    private UUID userUid;
    private String walletUid;
    private String walletName;
    private BigDecimal amount;
    private String type;
    private String state;
    private String paymentRequestDtoUid;
}
