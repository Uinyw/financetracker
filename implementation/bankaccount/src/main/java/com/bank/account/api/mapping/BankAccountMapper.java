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
        final BankAccountDto bankAccountDto = new BankAccountDto();
        bankAccountDto.setId(UUID.fromString(bankAccount.getId()));
        bankAccountDto.setName(bankAccount.getName());
        bankAccountDto.setDescription(bankAccount.getDescription());
        bankAccountDto.setBalance(mapMonetaryAmountModelToDto(bankAccount.getBalance()));
        bankAccountDto.setDispositionLimit(mapMonetaryAmountModelToDto(bankAccount.getDispositionLimit()));
        bankAccountDto.setLabels(mapLabelModelToDto(bankAccount.getLabels()));
        return bankAccountDto;
    }

    private MonetaryAmountDto mapMonetaryAmountModelToDto(final MonetaryAmount amount) {
        final var result = new MonetaryAmountDto();
        result.setAmount(amount.amount().doubleValue());
        return result;
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
