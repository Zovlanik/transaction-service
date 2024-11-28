package com.example.transaction_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "top_up_requests")
public class TopUpRequest {

    @Id
    @GeneratedValue
    private UUID uid;
    private LocalDateTime createdAt;
    private String provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_request_uid", referencedColumnName = "uid", nullable = false)
    private PaymentRequest paymentRequest;
}
