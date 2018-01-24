package com.pos.yza.yzapos.newtransaction.categoryselection;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.newtransaction.payment.PaymentContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Lopez on 18/1/18.
 */

public class CategorySelectionPresenter implements CategorySelectionContract.Presenter {

    private final CategorySelectionContract.View mPaymentView;


    public CategorySelectionPresenter(@NonNull CategorySelectionContract.View mPaymentView) {
        this.mPaymentView = checkNotNull(mPaymentView);
        mPaymentView.setPresenter(this);
    }

    @Override
    public void start() {

    }

}
