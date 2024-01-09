package com.financetracker.transaction.api;
import com.financetracker.transaction.api.mapping.DtoModelMapper;
import com.financetracker.transaction.logic.operations.TransactionService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.TransactionsApi;
import org.openapitools.model.OneTimeTransactionDto;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TransactionResource implements TransactionsApi {

    private final TransactionService transactionService;
    private final DtoModelMapper transactionMapper;

    @Override
    public List<OneTimeTransactionDto> transactionsOnetimeGet() {
        return transactionService.getOneTimeTransactions().stream()
                .map(transactionMapper::mapOneTimeTransactionModelToDto)
                .toList();
    }

    @Override
    public void transactionsOnetimePost(OneTimeTransactionDto oneTimeTransactionDto) {
        transactionService.createOneTimeTransactions(transactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));
    }

    @Override
    public void transactionsOnetimeIdDelete(String id) {

    }

    @Override
    public OneTimeTransactionDto transactionsOnetimeIdGet(String id) {
        return null;
    }

    @Override
    public void transactionsOnetimeIdPatch(String id, OneTimeTransactionDto oneTimeTransactionDto) {

    }


}
