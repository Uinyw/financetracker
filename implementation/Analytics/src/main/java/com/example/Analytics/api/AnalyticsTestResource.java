package com.example.Analytics.api;

import com.example.Analytics.api.mapping.TransactionMapper;
import com.example.Analytics.logic.model.budgetModel.VariableMonthlyTransaction;
import com.example.Analytics.logic.operations.BudgetService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.TestTransferApi;
import org.openapitools.client.model.MonetaryAmountDto;
import org.openapitools.client.model.TransferDto;
import org.openapitools.client.model.TransferStatusDto;
import org.openapitools.client.model.TypeDto;
import org.openapitools.model.OneTimeTransactionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AnalyticsTestResource implements TestTransferApi {

    private final BudgetService budgetService;
    private final TransactionMapper transactionMapper;

    @CrossOrigin(origins = "*")
    @Override
    public ResponseEntity<Void> testTransferPost(final OneTimeTransactionDto oneTimeTransactionDto) {
        List<VariableMonthlyTransaction> variableMonthlyTransactionList = transactionMapper.oneTimeTransactionDtoToVariableMonthlyTransaction(oneTimeTransactionDTOMapping(oneTimeTransactionDto));
        budgetService.variableMonthlyTransactionChange(variableMonthlyTransactionList);
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    private org.openapitools.client.model.OneTimeTransactionDto oneTimeTransactionDTOMapping(OneTimeTransactionDto oneTimeTransactionDto){
        return org.openapitools.client.model.OneTimeTransactionDto.builder()
                .id(oneTimeTransactionDto.getId())
                .type(typeDtoMapper(oneTimeTransactionDto.getType()))
                .date(oneTimeTransactionDto.getDate())
                .name(oneTimeTransactionDto.getName())
                .amount(typeDtoMapper(oneTimeTransactionDto.getAmount()))
                .description(oneTimeTransactionDto.getDescription())
                .labels(oneTimeTransactionDto.getLabels())
                .transfer(typeDtoMapper(oneTimeTransactionDto.getTransfer()))
                .transferStatus(typeDtoMapper(oneTimeTransactionDto.getTransferStatus()))
                .build();

    }

    private TransferStatusDto typeDtoMapper(org.openapitools.model.TransferStatusDto transferStatusDto){
        return switch (transferStatusDto){
            case INITIAL -> TransferStatusDto.INITIAL;
            case FAILED -> TransferStatusDto.FAILED;
            case SUCCESSFUL -> TransferStatusDto.SUCCESSFUL;
        };
    }
    private TransferDto typeDtoMapper(org.openapitools.model.TransferDto transferDto){
        return TransferDto.builder()
                .sourceBankAccountId(transferDto.getSourceBankAccountId())
                .externalTargetId(transferDto.getExternalTargetId())
                .targetBankAccountId(transferDto.getTargetBankAccountId())
                .externalSourceId(transferDto.getExternalSourceId())
                .build();
    }

    private MonetaryAmountDto typeDtoMapper(org.openapitools.model.MonetaryAmountDto monetaryAmountDto){
        return MonetaryAmountDto.builder().amount(monetaryAmountDto.getAmount()).build();
    }

    private TypeDto typeDtoMapper(org.openapitools.model.TypeDto typeDto){
        return switch (typeDto){
            case SHIFT -> TypeDto.SHIFT;
            case EXPENSE -> TypeDto.EXPENSE;
            case INCOME -> TypeDto.INCOME;
        };
    }

}
