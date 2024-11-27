package com.example.transaction_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "wallet_types")
@Data
public class WalletType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
