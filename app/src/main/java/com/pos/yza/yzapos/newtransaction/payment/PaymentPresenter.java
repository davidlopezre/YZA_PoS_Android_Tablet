package com.pos.yza.yzapos.newtransaction.payment;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.newtransaction.NewTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Lopez on 18/1/18.
 */

public class PaymentPresenter implements PaymentContract.Presenter {

    private final PaymentContract.View mPaymentView;
    private final NewTransaction transaction;


    public PaymentPresenter(@NonNull PaymentContract.View mPaymentView, @NonNull NewTransaction transaction) {
        this.mPaymentView = checkNotNull(mPaymentView);
        this.transaction = transaction;
        mPaymentView.setPresenter(this);

    }

    @Override
    public void start() {
        mPaymentView.setNumOfItems(transaction.getNumberOfItems());
        mPaymentView.setTotalDue(transaction.getTotalDue());
        mPaymentView.setChange(transaction.getChange());
    }

    @Override
    public void confirmPayment(Double amount) {
        mPaymentView.showConfirmPayment();
    }

    @Override
    public void setPaymentAmount(Double amount) {
        transaction.setPayment(amount);
        mPaymentView.setChange(transaction.getChange());
    }


}
