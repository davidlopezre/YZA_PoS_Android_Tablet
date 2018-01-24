package com.pos.yza.yzapos.newtransaction.productselection;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.newtransaction.payment.PaymentContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Lopez on 18/1/18.
 */

public class ProductSelectionPresenter implements ProductSelectionContract.Presenter {

    private final ProductSelectionContract.View mPaymentView;


    public ProductSelectionPresenter(@NonNull ProductSelectionContract.View mPaymentView) {
        this.mPaymentView = checkNotNull(mPaymentView);
        mPaymentView.setPresenter(this);
    }

    @Override
    public void start() {

    }

}
