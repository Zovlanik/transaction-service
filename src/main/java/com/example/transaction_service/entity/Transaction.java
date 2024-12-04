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
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue
    private UUID uid;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
    @Column(name = "user_uid", nullable = false)
    private UUID userUid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_uid", referencedColumnName = "uid", nullable = false)
    private Wallet wallet;
    @Column(name = "wallet_name", nullable = false, length = 32)
    private String walletName;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 32)
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, length = 32)
    private Status state;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_request_uid", referencedColumnName = "uid", nullable = false)
    private PaymentRequest paymentRequest;
}
