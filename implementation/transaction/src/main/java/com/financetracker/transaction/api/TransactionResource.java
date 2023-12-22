package com.financetracker.transaction.api;
import com.financetracker.transaction.api.mapping.DtoModelMapper;
import com.financetracker.transaction.logic.operations.TransactionService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.TransactionsApi;
import org.openapitools.model.OneTimeTransactionDto;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
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
    public void transactionsOnetimePost(final OneTimeTransactionDto oneTimeTransactionDto) {
        transactionService.createOneTimeTransaction(transactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));
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
        return transactionMapper.mapOneTimeTransactionModelToDto(
                transactionService.getOneTimeTransaction(id).orElseThrow(NotFoundException::new)
        );
    }

    @Override
    public void transactionsOnetimeIdPatch(String id, OneTimeTransactionDto oneTimeTransactionDto) {
        if (transactionService.getOneTimeTransaction(id).isEmpty()) {
            throw new NotFoundException();
        }

        transactionService.updateOneTimeTransaction(transactionMapper.mapOneTimeTransactionDtoToModel(oneTimeTransactionDto));
    }


}
