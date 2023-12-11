package com.financetracker.transaction;
import org.openapitools.api.TransactionsApi;
import org.openapitools.model.TransactionsGet200ResponseInner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class TransactionResource implements TransactionsApi {

    @Override
    public List<TransactionsGet200ResponseInner> transactionsGet() {
        return Collections.emptyList();
    }

    @Override
    public void transactionsIdDelete(String id) {

    }

    @Override
    public TransactionsGet200ResponseInner transactionsIdGet(String id) {
        return null;
    }

    @Override
    public void transactionsIdPatch(String id, TransactionsGet200ResponseInner transactionsGet200ResponseInner) {

    }

    @Override
    public void transactionsPost(TransactionsGet200ResponseInner transactionsGet200ResponseInner) {

    }
}
