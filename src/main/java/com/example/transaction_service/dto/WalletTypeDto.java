package com.example.transaction_service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class WalletTypeDto {
    private UUID uid;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String name;
    private String currencyCode;
    private String status;
    private LocalDateTime archivedAt;
    private String userType;
    private String creator;
    private String modifier;
}
