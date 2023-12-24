package com.financetracker.transaction.api.mapping;

import com.financetracker.transaction.api.exceptions.NotParseableException;
import com.financetracker.transaction.logic.model.*;
import org.openapitools.model.*;
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
public class TransactionMapper implements OneTimeTransactionMapper, RecurringTransactionMapper {

    @Override
    public OneTimeTransactionDto mapOneTimeTransactionModelToDto(final OneTimeTransaction oneTimeTransaction) {
        final var oneTimeTransactionDto = new OneTimeTransactionDto();
        oneTimeTransactionDto.setId(UUID.fromString(oneTimeTransaction.getId()));
        oneTimeTransactionDto.setName(oneTimeTransaction.getName());
        oneTimeTransactionDto.setDescription(oneTimeTransaction.getDescription());
        oneTimeTransactionDto.setType(mapTypeModelToDto(oneTimeTransaction.getType()));
        oneTimeTransactionDto.setLabels(mapLabelModelToDto(oneTimeTransaction.getLabels()));
        oneTimeTransactionDto.setTransfer(mapTransferModelToDto(oneTimeTransaction.getTransfer()));
        oneTimeTransactionDto.setAmount(mapMonetaryAmountModelToDto(oneTimeTransaction.getAmount()));
        oneTimeTransactionDto.setDate(oneTimeTransaction.getDate().toString());
        return oneTimeTransactionDto;
    }

    @Override
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

    @Override
    public RecurringTransactionDto mapRecurringTransactionModelToDto(final RecurringTransaction recurringTransaction) {
        final var recurringTransactionDto = new RecurringTransactionDto();
        recurringTransactionDto.setId(UUID.fromString(recurringTransaction.getId()));
        recurringTransactionDto.setName(recurringTransaction.getName());
        recurringTransactionDto.setDescription(recurringTransaction.getDescription());
        recurringTransactionDto.setType(mapTypeModelToDto(recurringTransaction.getType()));
        recurringTransactionDto.setLabels(mapLabelModelToDto(recurringTransaction.getLabels()));
        recurringTransactionDto.setTransfer(mapTransferModelToDto(recurringTransaction.getTransfer()));

        recurringTransactionDto.setPeriodicity(mapRecurringTypeModelToDto(recurringTransaction.getPeriodicity()));
        recurringTransactionDto.setFixedAmount(mapMonetaryAmountModelToDto(recurringTransaction.getFixedAmount()));

        final var transactionRecordDtoList = recurringTransaction.getTransactionRecords().stream()
                        .map(this::mapTransactionRecordModelToDto)
                        .toList();
        recurringTransactionDto.setTransactionRecords(transactionRecordDtoList);

        return recurringTransactionDto;
    }

    @Override
    public RecurringTransaction mapRecurringTransactionDtoToModel(final RecurringTransactionDto recurringTransactionDto) {
        final var transactionRecords = recurringTransactionDto.getTransactionRecords().stream()
                .map(dto -> mapTransactionRecordDtoToModel(recurringTransactionDto.getId().toString(), dto))
                .collect(Collectors.toSet());

        return RecurringTransaction.with()
                .id(recurringTransactionDto.getId().toString())
                .name(recurringTransactionDto.getName())
                .description(recurringTransactionDto.getDescription())
                .type(mapTypeDtoToModel(recurringTransactionDto.getType()))
                .labels(mapLabelDtoToModel(recurringTransactionDto.getLabels()))
                .transfer(mapTransferDtoToModel(recurringTransactionDto.getTransfer()))
                .periodicity(mapRecurringTypeModelToDto(recurringTransactionDto.getPeriodicity()))
                .fixedAmount(mapMonetaryAmountDtoToModel(recurringTransactionDto.getFixedAmount()))
                .transactionRecords(transactionRecords)
                .build();
    }

    private TransactionRecordDto mapTransactionRecordModelToDto(final TransactionRecord transactionRecord) {
        final var result = new TransactionRecordDto();
        result.setAmount(mapMonetaryAmountModelToDto(transactionRecord.getAmount()));
        result.setDate(transactionRecord.getDate().toString());
        return result;
    }

    private TransactionRecord mapTransactionRecordDtoToModel(final String transactionId, final TransactionRecordDto transactionRecordDto) {
        return new TransactionRecord(UUID.randomUUID().toString(),
                transactionId,
                mapDateDtoToModel(transactionRecordDto.getDate()),
                mapMonetaryAmountDtoToModel(transactionRecordDto.getAmount()));
    }

    private TypeDto mapTypeModelToDto(final Type type) {
        return switch (type) {
            case INCOME -> TypeDto.INCOME;
            case EXPENSE -> TypeDto.EXPENSE;
        };
    }

    private PeriodicityDto mapRecurringTypeModelToDto(final Periodicity periodicity) {
        return switch (periodicity) {
            case MONTHLY -> PeriodicityDto.MONTHLY;
            case QUARTERLY -> PeriodicityDto.QUARTERLY;
            case HALF_YEARLY -> PeriodicityDto.HALF_YEARLY;
            case YEARLY -> PeriodicityDto.YEARLY;
        };
    }

    private Periodicity mapRecurringTypeModelToDto(@Nullable final PeriodicityDto periodicityDto) {
        if (periodicityDto == null) {
            throw new NotParseableException();
        }

        return switch (periodicityDto) {
            case MONTHLY -> Periodicity.MONTHLY;
            case QUARTERLY -> Periodicity.QUARTERLY;
            case HALF_YEARLY -> Periodicity.HALF_YEARLY;
            case YEARLY -> Periodicity.YEARLY;
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

    private Type mapTypeDtoToModel(@Nullable final TypeDto typeDto) {
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
