package com.financetracker.transaction.api.mapping;

import com.financetracker.transaction.logic.model.*;
import com.financetracker.transaction.utils.UUIDGenerator;
import org.openapitools.model.MonetaryAmountDto;
import org.openapitools.model.OneTimeTransactionDto;
import org.openapitools.model.TransferDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TransactionMapper implements DtoModelMapper {

    public OneTimeTransactionDto mapOneTimeTransactionModelToDto(final OneTimeTransaction oneTimeTransaction) {
        final var oneTimeTransactionDto = new OneTimeTransactionDto();
        oneTimeTransactionDto.setId(UUIDGenerator.fromString(oneTimeTransaction.getId()));
        oneTimeTransactionDto.setName(oneTimeTransaction.getName());
        oneTimeTransactionDto.setDescription(oneTimeTransaction.getDescription());
        oneTimeTransactionDto.setType(mapTypeModelToDto(oneTimeTransaction.getType()));
        oneTimeTransactionDto.setLabels(oneTimeTransaction.getLabels() != null ? oneTimeTransaction.getLabels().stream().map(Label::getName).toList() : new ArrayList<>());
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

    private MonetaryAmountDto mapMonetaryAmountModelToDto(final MonetaryAmount amount) {
        final var result = new MonetaryAmountDto();
        result.setAmount(amount.amount().doubleValue());
        return result;
    }

    private TransferDto mapTransferModelToDto(final Transfer transfer) {
        final var result = new TransferDto();
        result.setSourceId(transfer.sourceId());
        result.setTargetBankAccountId(UUIDGenerator.fromString(transfer.targetBankAccountId()));
        return result;
    }

    public OneTimeTransaction mapOneTimeTransactionDtoToModel(final OneTimeTransactionDto oneTimeTransactionDto) {
        return OneTimeTransaction.with()
                .id(UUIDGenerator.toString(oneTimeTransactionDto.getId()))
                .name(oneTimeTransactionDto.getName())
                .description(oneTimeTransactionDto.getDescription())
                .type(mapTypeDtoToModel(oneTimeTransactionDto.getType()))
                .labels(mapLabelDtoToModel(oneTimeTransactionDto.getId().toString(), oneTimeTransactionDto.getLabels()))
                .transfer(mapTransferDtoToModel(oneTimeTransactionDto.getTransfer()))
                .amount(mapMonetaryAmountDtoToModel(oneTimeTransactionDto.getAmount()))
                .date(LocalDate.parse(oneTimeTransactionDto.getDate()))
                .build();
    }

    private Set<Label> mapLabelDtoToModel(final String transactionId, final List<String> labels) {
        if (labels == null) {
            return Collections.emptySet();
        }

        return labels.stream()
                .map(label -> new Label(transactionId, label))
                .collect(Collectors.toSet());
    }

    private Type mapTypeDtoToModel(final OneTimeTransactionDto.TypeEnum typeDto) {
        return switch (typeDto) {
            case INCOME -> Type.INCOME;
            case EXPENSE -> Type.EXPENSE;
        };
    }

    private MonetaryAmount mapMonetaryAmountDtoToModel(final MonetaryAmountDto amountDto) {
        return new MonetaryAmount(BigDecimal.valueOf(amountDto.getAmount()));
    }

    private Transfer mapTransferDtoToModel(final TransferDto transferDto) {
        return new Transfer(transferDto.getSourceId(), UUIDGenerator.toString(transferDto.getTargetBankAccountId()));
    }
}
