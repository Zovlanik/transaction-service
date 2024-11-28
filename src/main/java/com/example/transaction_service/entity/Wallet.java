package com.example.transaction_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID uid;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "modified_at")
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
