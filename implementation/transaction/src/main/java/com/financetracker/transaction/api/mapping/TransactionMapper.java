package com.financetracker.transaction.api.mapping;

import com.financetracker.transaction.api.exceptions.NotParseableException;
import com.financetracker.transaction.logic.model.*;
import com.financetracker.transaction.utils.UUIDGenerator;
import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.TransferDto;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionMapper implements OneTimeTransactionMapper {

    public OneTimeTransactionDto mapOneTimeTransactionModelToDto(final OneTimeTransaction oneTimeTransaction) {
        final var oneTimeTransactionDto = new OneTimeTransactionDto();
        oneTimeTransactionDto.setId(UUIDGenerator.fromString(oneTimeTransaction.getId()));
        oneTimeTransactionDto.setName(oneTimeTransaction.getName());
        oneTimeTransactionDto.setDescription(oneTimeTransaction.getDescription());
        oneTimeTransactionDto.setType(mapTypeModelToDto(oneTimeTransaction.getType()));
        oneTimeTransactionDto.setLabels(mapLabelModelToDto(oneTimeTransaction.getLabels()));
        oneTimeTransactionDto.setTransfer(mapTransferModelToDto(oneTimeTransaction.getTransfer()));
        oneTimeTransactionDto.setAmount(mapMonetaryAmountModelToDto(oneTimeTransaction.getAmount()));
        oneTimeTransactionDto.setDate(oneTimeTransaction.getDate().toString());
        return oneTimeTransactionDto;
    }

    private OneTimeTransactionDto.TypeEnum mapTypeModelToDto(final Type type) {
        return switch (type) {
            case INCOME -> OneTimeTransactionDto.TypeEnum.INCOME;
            case EXPENSE -> OneTimeTransactionDto.TypeEnum.EXPENSE;
        };
    }

    private List<String> mapLabelModelToDto(final Set<Label> labels) {
        return labels.stream().map(Label::name).toList();
    }

    private MonetaryAmountDto mapMonetaryAmountModelToDto(final MonetaryAmount amount) {
        final var result = new MonetaryAmountDto();
        result.setAmount(amount.amount().doubleValue());
        return result;
    }

    private TransferDto mapTransferModelToDto(final Transfer transfer) {
        final var result = new TransferDto();
        result.setSourceBankAccountId(UUID.fromString(transfer.sourceBankAccountId()));
        result.setExternalSourceId(transfer.externalSourceId());
        result.setTargetBankAccountId(UUID.fromString(transfer.targetBankAccountId()));
        return result;
    }

    public OneTimeTransaction mapOneTimeTransactionDtoToModel(final OneTimeTransactionDto oneTimeTransactionDto) {
        return OneTimeTransaction.with()
                .id(oneTimeTransactionDto.getId().toString())
                .name(oneTimeTransactionDto.getName())
                .description(oneTimeTransactionDto.getDescription())
                .type(mapTypeDtoToModel(oneTimeTransactionDto.getType()))
                .labels(mapLabelDtoToModel(oneTimeTransactionDto.getLabels()))
                .transfer(mapTransferDtoToModel(oneTimeTransactionDto.getTransfer()))
                .amount(mapMonetaryAmountDtoToModel(oneTimeTransactionDto.getAmount()))
                .date(mapDateDtoToModel(oneTimeTransactionDto.getDate()))
                .build();
    }

    private Type mapTypeDtoToModel(@Nullable final OneTimeTransactionDto.TypeEnum typeDto) {
        if (typeDto == null) {
            throw new NotParseableException();
        }

        return switch (typeDto) {
            case INCOME -> Type.INCOME;
            case EXPENSE -> Type.EXPENSE;
        };
    }

    private Set<Label> mapLabelDtoToModel(final List<String> labels) {
        if (labels == null) {
            return Collections.emptySet();
        }

        return labels.stream()
                .map(Label::new)
                .collect(Collectors.toSet());
    }

    private Transfer mapTransferDtoToModel(final TransferDto transferDto) {
        if (transferDto == null || (transferDto.getSourceBankAccountId() == null && transferDto.getExternalSourceId() == null)) {
            throw new NotParseableException();
        }
        
        return new Transfer(transferDto.getSourceBankAccountId() != null ? transferDto.getSourceBankAccountId().toString() : null, transferDto.getExternalSourceId(), transferDto.getTargetBankAccountId().toString());
    }

    private MonetaryAmount mapMonetaryAmountDtoToModel(final MonetaryAmountDto amountDto) {
        return new MonetaryAmount(BigDecimal.valueOf(amountDto.getAmount()));
    }

    private LocalDate mapDateDtoToModel(final String dateDto) {
        try {
            return LocalDate.parse(dateDto);
        } catch (NullPointerException | DateTimeParseException e) {
            throw new NotParseableException();
        }
    }
}
