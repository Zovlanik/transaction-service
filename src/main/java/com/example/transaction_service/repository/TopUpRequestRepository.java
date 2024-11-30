package com.example.transaction_service.repository;

import com.example.transaction_service.entity.TopUpRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TopUpRequestRepository extends JpaRepository<TopUpRequest, UUID> {
}
