package com.pos.yza.yzapos.managetransactions;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.Item;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.ProductsDataSource;
import com.pos.yza.yzapos.data.source.TransactionsDataSource;
import com.pos.yza.yzapos.data.source.TransactionsRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Lopez on 22/02/18.
 */

public class ManageTransactionsPresenter implements ManageTransactionsContract.Presenter {
    private final ManageTransactionsContract.View mManageTransactionsView;

    private final TransactionsRepository mTransactionsRepository;

    public ManageTransactionsPresenter(@NonNull TransactionsRepository transactionsRepository,
                                       @NonNull ManageTransactionsContract.View view){
        mTransactionsRepository = transactionsRepository;
        mManageTransactionsView = view;
        mManageTransactionsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadTransactions();
    }

    @Override
    public void loadTransactions() {
        mTransactionsRepository.getTransactions(new TransactionsDataSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<Transaction> transactions) {
                mManageTransactionsView.showTransactions(new ArrayList<Transaction>(transactions));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void cancelTransaction(String transactionId) {
        mTransactionsRepository.refundTransaction(transactionId);
        mTransactionsRepository.cancelTransaction(transactionId);
    }

    @Override
    public void refundTransaction(String transactionId) {
        mTransactionsRepository.refundTransaction(transactionId);
    }

    @Override
    public void addPaymentToTransaction() {

    }
}
