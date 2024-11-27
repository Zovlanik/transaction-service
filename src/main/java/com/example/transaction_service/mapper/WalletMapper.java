package com.example.transaction_service.mapper;

import com.example.transaction_service.dto.WalletDtoRequest;
import com.example.transaction_service.dto.WalletDtoResponse;
import com.example.transaction_service.entity.Wallet;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletMapper {
    @InheritInverseConfiguration
//    @Mapping(target = "walletType", source = "walletTypeId", qualifiedByName = "walletTypeIdToWalletType")
    Wallet map(WalletDtoRequest walletDtoRequest);
    WalletDtoRequest map(Wallet wallet);
    WalletDtoResponse mapRs(Wallet wallet);

    void updateWalletFromDto(WalletDtoRequest dto, @MappingTarget Wallet entity);



//    @Named("walletTypeIdToWalletType")
//    default WalletType walletTypeIdToWalletType(String walletTypeId) {
//        return WalletType.builder()
//                .id(UUID.fromString(walletTypeId))
//                .build();
//    }
}
