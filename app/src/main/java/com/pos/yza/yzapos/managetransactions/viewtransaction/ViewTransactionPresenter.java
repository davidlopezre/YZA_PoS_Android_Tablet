package com.pos.yza.yzapos.managetransactions.viewtransaction;


import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.representations.LineItem;
import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.data.source.PaymentsRepository;
import com.pos.yza.yzapos.data.source.TransactionsRepository;

public class ViewTransactionPresenter implements ViewTransactionContract.Presenter {

    private final ViewTransactionContract.View mViewTransactionView;
    private final TransactionsRepository mTransactionsRepository;
    private final PaymentsRepository mPaymentsRepository;
    private final Transaction transaction;

    public ViewTransactionPresenter(@NonNull TransactionsRepository transactionsRepository,
                                    @NonNull PaymentsRepository paymentsRepository,
                                    @NonNull ViewTransactionContract.View view,
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
