package com.example.transaction_service.repository;

import com.example.transaction_service.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    List<Wallet> findAllByUserUid (UUID userUid);
    @Query("SELECT w FROM wallets w JOIN w.walletType wt WHERE w.userUid = :userUid AND wt.currencyCode = :currency")
    List<Wallet> findAllByUserUidAndCurrency (@Param("userUid") UUID userUid, @Param("currency") String currency);
}
