package com.example.transaction_service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class WalletDtoRequest {

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String name;
    private UUID walletTypeUid;
    private UUID userUid;
    private String status;
    private BigDecimal balance;
    private LocalDateTime archivedAt;
}
