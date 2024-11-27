package com.example.transaction_service.repository;

import com.example.transaction_service.entity.WalletType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletTypeRepository extends JpaRepository<WalletType, UUID> {

}
