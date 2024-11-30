package com.example.transaction_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "transfer_requests")
public class TransferRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID uid;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private String systemRate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_request_uid_from", referencedColumnName = "uid", nullable = false)
    private PaymentRequest paymentRequestFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_request_uid_to", referencedColumnName = "uid", nullable = false)
    private PaymentRequest paymentRequestTo;
}
