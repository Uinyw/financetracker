package com.financetracker.transaction.api.mapping;

import com.financetracker.transaction.api.exceptions.NotParseableException;
import com.financetracker.transaction.logic.model.*;
import org.openapitools.model.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TransactionMapper implements OneTimeTransactionMapper, RecurringTransactionMapper {

    @Override
    public OneTimeTransactionDto mapOneTimeTransactionModelToDto(final OneTimeTransaction oneTimeTransaction) {
        return OneTimeTransactionDto.builder()
                .id(UUID.fromString(oneTimeTransaction.getId()))
                .name(oneTimeTransaction.getName())
                .description(oneTimeTransaction.getDescription())
                .type(mapTypeModelToDto(oneTimeTransaction.getType()))
                .labels(mapLabelModelToDto(oneTimeTransaction.getLabels()))
                .transfer(mapTransferModelToDto(oneTimeTransaction.getTransfer()))
                .amount(mapMonetaryAmountModelToDto(oneTimeTransaction.getAmount()))
                .date(oneTimeTransaction.getDate().toString())
                .transferStatus(mapTransferStatusModelToDto(oneTimeTransaction.getTransferStatus()))
                .build();
    }

    @Override
    public OneTimeTransaction mapOneTimeTransactionDtoToModel(final OneTimeTransactionDto oneTimeTransactionDto) {
        return OneTimeTransaction.with()
                .id(oneTimeTransactionDto.getId().toString())
                .name(oneTimeTransactionDto.getName())
                .description(oneTimeTransactionDto.getDescription())
                .type(mapTypeDtoToModel(oneTimeTransactionDto.getType()))
                .labels(mapLabelDtoToModel(oneTimeTransactionDto.getLabels()))
                .transfer(mapTransferDtoToModel(oneTimeTransactionDto.getTransfer(), oneTimeTransactionDto.getType()))
                .amount(mapMonetaryAmountDtoToModel(oneTimeTransactionDto.getAmount()))
                .date(mapDateDtoToModel(oneTimeTransactionDto.getDate()))
                .build();
    }

    @Override
    public RecurringTransactionDto mapRecurringTransactionModelToDto(final RecurringTransaction recurringTransaction) {
        return RecurringTransactionDto.builder()
                .id(UUID.fromString(recurringTransaction.getId()))
                .name(recurringTransaction.getName())
                .description(recurringTransaction.getDescription())
                .type(mapTypeModelToDto(recurringTransaction.getType()))
                .labels(mapLabelModelToDto(recurringTransaction.getLabels()))
                .transfer(mapTransferModelToDto(recurringTransaction.getTransfer()))
                .periodicity(mapRecurringTypeModelToDto(recurringTransaction.getPeriodicity()))
                .fixedAmount(mapMonetaryAmountModelToDto(recurringTransaction.getFixedAmount()))
                .transactionRecords(recurringTransaction.getTransactionRecords().stream()
                        .map(this::mapTransactionRecordModelToDto)
                        .toList())
                .build();
    }

    @Override
    public RecurringTransaction mapRecurringTransactionDtoToModel(final RecurringTransactionDto recurringTransactionDto) {
        final var transactionRecords = recurringTransactionDto.getTransactionRecords() != null ? recurringTransactionDto.getTransactionRecords().stream()
                .map(dto -> mapTransactionRecordDtoToModel(recurringTransactionDto.getId().toString(), dto))
                .collect(Collectors.toSet()) : new HashSet<TransactionRecord>();

        return RecurringTransaction.with()
                .id(recurringTransactionDto.getId().toString())
                .name(recurringTransactionDto.getName())
                .description(recurringTransactionDto.getDescription())
                .type(mapTypeDtoToModel(recurringTransactionDto.getType()))
                .labels(mapLabelDtoToModel(recurringTransactionDto.getLabels()))
                .transfer(mapTransferDtoToModel(recurringTransactionDto.getTransfer(), recurringTransactionDto.getType()))
                .startDate(mapDateDtoToModel(recurringTransactionDto.getStartDate()))
                .periodicity(mapRecurringTypeModelToDto(recurringTransactionDto.getPeriodicity()))
                .fixedAmount(recurringTransactionDto.getFixedAmount() != null ? mapMonetaryAmountDtoToModel(recurringTransactionDto.getFixedAmount()) : null)
                .transactionRecords(transactionRecords)
                .build();
    }

    private TransactionRecordDto mapTransactionRecordModelToDto(final TransactionRecord transactionRecord) {
        return TransactionRecordDto.builder()
                .id(UUID.fromString(transactionRecord.getId()))
                .amount(mapMonetaryAmountModelToDto(transactionRecord.getAmount()))
                .date(transactionRecord.getDate().toString())
                .transferStatus(mapTransferStatusModelToDto(transactionRecord.getTransferStatus()))
                .build();
    }

    public TransactionRecord mapTransactionRecordDtoToModel(final String transactionId, final TransactionRecordDto transactionRecordDto) {
        return new TransactionRecord(transactionRecordDto.getId().toString(),
                transactionId,
                mapDateDtoToModel(transactionRecordDto.getDate()),
                mapMonetaryAmountDtoToModel(transactionRecordDto.getAmount()),
                TransferStatus.INITIAL);
    }

    private TypeDto mapTypeModelToDto(final Type type) {
        return switch (type) {
            case SHIFT -> TypeDto.SHIFT;
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

    private TransferStatusDto mapTransferStatusModelToDto(final TransferStatus transferStatus) {
        return switch (transferStatus) {
            case INITIAL -> TransferStatusDto.INITIAL;
            case SUCCESSFUL -> TransferStatusDto.SUCCESSFUL;
            case FAILED -> TransferStatusDto.FAILED;
        };
    }

    private List<String> mapLabelModelToDto(final Set<Label> labels) {
        return labels.stream().map(Label::name).toList();
    }

    private MonetaryAmountDto mapMonetaryAmountModelToDto(final MonetaryAmount amount) {
        return MonetaryAmountDto.builder()
                .amount(amount.amount().doubleValue())
                .build();
    }

    private TransferDto mapTransferModelToDto(final Transfer transfer) {
        return TransferDto.builder()
                .sourceBankAccountId(transfer.getSourceBankAccountId() != null ? UUID.fromString(transfer.getSourceBankAccountId()) : null)
                .externalSourceId(transfer.getExternalSourceId())
                .targetBankAccountId(transfer.getTargetBankAccountId() != null ? UUID.fromString(transfer.getTargetBankAccountId()) : null)
                .externalTargetId(transfer.getExternalTargetId())
                .build();
    }

    private Type mapTypeDtoToModel(@Nullable final TypeDto typeDto) {
        if (typeDto == null) {
            throw new NotParseableException();
        }

        return switch (typeDto) {
            case SHIFT -> Type.SHIFT;
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

    private Transfer mapTransferDtoToModel(final TransferDto transferDto, final TypeDto type) {
        if (transferDto == null) {
            throw new NotParseableException();
        }
        
        final var transfer = new Transfer(transferDto.getSourceBankAccountId() != null ? transferDto.getSourceBankAccountId().toString() : null,
                transferDto.getExternalSourceId(),
                transferDto.getTargetBankAccountId() != null ? transferDto.getTargetBankAccountId().toString() : null,
                transferDto.getExternalTargetId());

        if ((type.equals(TypeDto.SHIFT) && !transfer.isShift()) || (type.equals(TypeDto.INCOME) && !transfer.isIncome()) || (type.equals(TypeDto.EXPENSE) && !transfer.isExpense())) {
            throw new NotParseableException();
        }

        return transfer;
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
