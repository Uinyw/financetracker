package com.financetracker.transaction.api;
import com.financetracker.transaction.api.mapping.OneTimeTransactionMapper;
import com.financetracker.transaction.logic.operations.TransactionService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.TransactionsApi;
import org.openapitools.model.OneTimeTransactionDto;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.NotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TransactionResource implements TransactionsApi {

    private final TransactionService transactionService;
    private final OneTimeTransactionMapper oneTimeTransactionMapper;

    @Override
    public List<OneTimeTransactionDto> transactionsOnetimeGet() {
        return transactionService.getOneTimeTransactions().stream()
                .map(oneTimeTransactionMapper::mapOneTimeTransactionModelToDto)
                .toList();
    }

    @Override
    public void transactionsOnetimePost(final OneTimeTransactionDto oneTimeTransactionDto) {
        transactionService.createOneTimeTransaction(oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));
    }

    @Override
    public void transactionsOnetimeIdDelete(final String id) {
        if (transactionService.getOneTimeTransaction(id).isEmpty()) {
            throw new NotFoundException();
        }
        transactionService.deleteOneTimeTransaction(id);
    }

    @Override
    public OneTimeTransactionDto transactionsOnetimeIdGet(final String id) {
        final var oneTimeTransaction = transactionService.getOneTimeTransaction(id).orElseThrow(NotFoundException::new);
        return oneTimeTransactionMapper.mapOneTimeTransactionModelToDto(oneTimeTransaction);
    }

    @Override
    public void transactionsOnetimeIdPatch(String id, OneTimeTransactionDto oneTimeTransactionDto) {
        if (transactionService.getOneTimeTransaction(id).isEmpty()) {
            throw new NotFoundException();
        }

        transactionService.updateOneTimeTransaction(oneTimeTransactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));
    }


}
