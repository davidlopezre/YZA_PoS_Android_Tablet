package com.pos.yza.yzapos.managetransactions;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.data.source.PaymentsRepository;
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
    private final PaymentsRepository mPaymentsRepository;
    private final String TAG = "MNG_TRANS_PRES";

    public ManageTransactionsPresenter(@NonNull TransactionsRepository transactionsRepository,
                                       @NonNull PaymentsRepository paymentsRepository,
                                       @NonNull ManageTransactionsContract.View view){
        mTransactionsRepository = transactionsRepository;
        mPaymentsRepository = paymentsRepository;
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

    @Override
    public void searchTransactionById(int id) {
        Log.i(TAG, "search for transaction #" + id);
        mTransactionsRepository.getTransactionById(id + "", new TransactionsDataSource.GetTransactionCallback() {
            @Override
            public void onTransactionLoaded(Transaction transaction) {
                ArrayList<Transaction> transactions = new ArrayList<>();
                transactions.add(transaction);
                mManageTransactionsView.setTransactions(transactions);
            }
            @Override
            public void onDataNotAvailable() {
                mManageTransactionsView.showSnackBar("Transaction ID not found");
            }
        });
    }
}
