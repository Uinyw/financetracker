package com.bank.account.api.mapping;

import com.bank.account.logic.model.BankAccount;
import com.bank.account.logic.model.Label;
import com.bank.account.logic.model.MonetaryAmount;
import org.openapitools.model.BankAccountDto;
import org.openapitools.model.MonetaryAmountDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BankAccountMapper {

    public BankAccount mapBankAccountDtoToModel(final BankAccountDto bankAccountDto) {
        return BankAccount.with()
                .id(bankAccountDto.getId().toString())
                .name(bankAccountDto.getName())
                .description(bankAccountDto.getDescription())
                .balance(mapMonetaryAmountDtoToModel(bankAccountDto.getBalance()))
                .dispositionLimit(bankAccountDto.getDispositionLimit() != null ? mapMonetaryAmountDtoToModel(bankAccountDto.getDispositionLimit()) : MonetaryAmount.DEFAULT)
                .labels(mapLabelDtoToModel(bankAccountDto.getLabels()))
                .build();
    }

    public BankAccountDto mapBankAccountModelToDtp(final BankAccount bankAccount) {
        return BankAccountDto.builder()
                .id(UUID.fromString(bankAccount.getId()))
                .name(bankAccount.getName())
                .description(bankAccount.getDescription())
                .balance(mapMonetaryAmountModelToDto(bankAccount.getBalance()))
                .dispositionLimit(mapMonetaryAmountModelToDto(bankAccount.getDispositionLimit()))
                .labels(mapLabelModelToDto(bankAccount.getLabels()))
                .build();
    }

    private MonetaryAmountDto mapMonetaryAmountModelToDto(final MonetaryAmount amount) {
        return MonetaryAmountDto.builder()
                .amount(amount.amount().doubleValue())
                .build();
    }

    private MonetaryAmount mapMonetaryAmountDtoToModel(final MonetaryAmountDto amountDto) {
        return new MonetaryAmount(BigDecimal.valueOf(amountDto.getAmount()));
    }

    private List<String> mapLabelModelToDto(final Set<Label> labels) {
        return labels.stream().map(Label::name).toList();
    }

    private Set<Label> mapLabelDtoToModel(final List<String> labels) {
        if (labels == null) {
            return Collections.emptySet();
        }

        return labels.stream()
                .map(Label::new)
                .collect(Collectors.toSet());
    }

}
