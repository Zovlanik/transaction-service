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
@Table(name = "payment_requests")
public class PaymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID uid;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
    private UUID userUid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_uid", referencedColumnName = "uid", nullable = false)
    private Wallet wallet;
    private BigDecimal amount;
    private String status;
    private String comment;
    private Long paymentMethodId;
}
