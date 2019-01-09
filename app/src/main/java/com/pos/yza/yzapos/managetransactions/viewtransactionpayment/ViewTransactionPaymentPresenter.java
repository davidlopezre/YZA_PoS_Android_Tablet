package com.pos.yza.yzapos.managetransactions.viewtransactionpayment;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.SessionStorage;
import com.pos.yza.yzapos.data.representations.Payment;
import com.pos.yza.yzapos.data.representations.Transaction;
import com.pos.yza.yzapos.data.source.PaymentsRepository;
import com.pos.yza.yzapos.data.source.TransactionsDataSource;
import com.pos.yza.yzapos.data.source.TransactionsRepository;

public class ViewTransactionPaymentPresenter implements ViewTransactionPaymentContract.Presenter{

    private final ViewTransactionPaymentContract.View mViewTransactionPaymentView;
    private final TransactionsRepository mTransactionsRepository;
    private final PaymentsRepository mPaymentsRepository;
    private Transaction transaction;

    private final String TAG = "VIEW_TRANS_PAY_PRES";

    public ViewTransactionPaymentPresenter(@NonNull TransactionsRepository transactionsRepository,
                                        @NonNull PaymentsRepository paymentsRepository,
                                        @NonNull ViewTransactionPaymentContract.View view,
                                        @NonNull Transaction transaction) {
        mTransactionsRepository = transactionsRepository;
        mPaymentsRepository = paymentsRepository;
        mViewTransactionPaymentView = view;
        mViewTransactionPaymentView.setPresenter(this);
        this.transaction = transaction;
    }

    @Override
    public void start() {
        mViewTransactionPaymentView.setPayments(transaction.getPayments());
    }

    @Override
    public double getTotalDue() {
        return transaction.getAmount();
    }

    @Override
    public double getBalance() {
        return getTotalDue() - getTotalPaid();
    }

    @Override
    public double getTotalPaid() {
        double totalPaid = 0;
        for (Payment p: transaction.getPayments()) {
            totalPaid += p.getAmount();
        }
        return totalPaid;
    }

    @Override
    public void makePayment(double payment) {
        if(payment <= 0) {
            return;
        }
        payment = Math.min(getBalance(), payment);
        mPaymentsRepository.savePayment(new Payment(
                payment, SessionStorage.getBranch().getBranchId(),
                transaction));
    }

    @Override
    public void reloadTransaction() {
        mTransactionsRepository.getTransactionById(
                Integer.toString(transaction.getTransactionId()),
                new TransactionsDataSource.GetTransactionCallback() {
                    @Override
                    public void onTransactionLoaded(Transaction transaction) {
                        setTransaction(transaction);
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
    }

    private void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        Log.i(TAG, "setting new transaction and payments");
        mViewTransactionPaymentView.setPayments(transaction.getPayments());
    }
}
