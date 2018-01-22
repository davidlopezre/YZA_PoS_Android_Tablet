package com.pos.yza.yzapos.newtransaction.customerdetails;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.newtransaction.cart.CartContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Lopez on 18/1/18.
 */

public class CustomerDetailsPresenter implements CustomerDetailsContract.Presenter {

    private final CustomerDetailsContract.View mCartView;


    public CustomerDetailsPresenter(@NonNull CustomerDetailsContract.View mCartView) {
        this.mCartView = checkNotNull(mCartView);
        mCartView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void goToPayment() {

    }

    @Override
    public void confirm() {

    }
}
