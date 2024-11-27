package com.example.transaction_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "wallets")
@Data
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID uid;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wallet_type_uid", referencedColumnName = "uid")
    private WalletType walletType;
    private UUID userUid;
    private String status;
    private BigDecimal balance;
    private LocalDateTime archivedAt;

}
