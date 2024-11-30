package com.example.transaction_service.dto.wallet;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class WalletDtoResponse {
    private UUID uid;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String name;
    private WalletTypeDto walletType;
    private UUID userUid;
    private String status;
    private BigDecimal balance;
    private LocalDateTime archivedAt;
}
