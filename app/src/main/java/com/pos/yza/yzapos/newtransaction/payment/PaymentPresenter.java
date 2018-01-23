package com.pos.yza.yzapos.newtransaction.payment;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.newtransaction.customerdetails.CustomerDetailsContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Lopez on 18/1/18.
 */

public class PaymentPresenter implements PaymentContract.Presenter {

    private final PaymentContract.View mPaymentView;


    public PaymentPresenter(@NonNull PaymentContract.View mPaymentView) {
        this.mPaymentView = checkNotNull(mPaymentView);
        mPaymentView.setPresenter(this);
    }

    @Override
    public void start() {

    }

}
