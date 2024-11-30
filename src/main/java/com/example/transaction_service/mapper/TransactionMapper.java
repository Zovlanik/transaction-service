package com.example.transaction_service.mapper;

import com.example.transaction_service.dto.payment.TransactionDto;
import com.example.transaction_service.entity.Transaction;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @InheritInverseConfiguration
    Transaction map(TransactionDto transactionDto);
    @Mapping(target = "paymentRequestDtoUid", source = "paymentRequest.uid")
    @Mapping(target = "walletUid", source = "wallet.uid")
    TransactionDto map(Transaction transaction);

}
