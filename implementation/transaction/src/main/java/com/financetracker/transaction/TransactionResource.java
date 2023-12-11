package com.financetracker.transaction;
import org.openapitools.api.TransactionApi;
import org.openapitools.model.TransactionsGet200ResponseInner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TransactionResource implements TransactionApi {


    @Override
    public List<TransactionsGet200ResponseInner> transactionsGet() {
        final var x = new TransactionsGet200ResponseInner();
        x.setDescription("Hallo");
        return List.of(x);
    }

    @Override
    public TransactionsGet200ResponseInner transactionsIdDelete(String id) {
        return null;
    }

    @Override
    public TransactionsGet200ResponseInner transactionsIdGet(String id) {
        return null;
    }

    @Override
    public TransactionsGet200ResponseInner transactionsIdPatch(String id, TransactionsGet200ResponseInner transactionsGet200ResponseInner) {
        return null;
    }

    @Override
    public TransactionsGet200ResponseInner transactionsPost(TransactionsGet200ResponseInner transactionsGet200ResponseInner) {
        return null;
    }
}
