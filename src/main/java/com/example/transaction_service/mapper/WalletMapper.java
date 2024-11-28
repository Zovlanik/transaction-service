package com.example.transaction_service.mapper;

import com.example.transaction_service.dto.WalletDtoRequest;
import com.example.transaction_service.dto.WalletDtoResponse;
import com.example.transaction_service.entity.Wallet;
import org.mapstruct.*;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletMapper {
    @InheritInverseConfiguration
    Wallet map(WalletDtoRequest walletDtoRequest);
    WalletDtoResponse map(Wallet wallet);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    void updateWalletFromDto(WalletDtoRequest dto, @MappingTarget Wallet entity);

}
