package com.pos.yza.yzapos.managetransactions.viewtransactioncart;


import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.data.source.PaymentsRepository;
import com.pos.yza.yzapos.data.source.TransactionsRepository;

public class ViewTransactionCartPresenter implements ViewTransactionCartContract.Presenter {

    private final ViewTransactionCartContract.View mViewTransactionView;
    private final TransactionsRepository mTransactionsRepository;
    private final PaymentsRepository mPaymentsRepository;
    private final Transaction transaction;

    public ViewTransactionCartPresenter(@NonNull TransactionsRepository transactionsRepository,
                                        @NonNull PaymentsRepository paymentsRepository,
                                        @NonNull ViewTransactionCartContract.View view,
                                        @NonNull Transaction transaction){
        mTransactionsRepository = transactionsRepository;
        mPaymentsRepository = paymentsRepository;
        mViewTransactionView = view;
        mViewTransactionView.setPresenter(this);
        this.transaction = transaction;
    }

    @Override
    public void start() {
        mViewTransactionView.setLineItems(transaction.getLineItems());
    }

}
