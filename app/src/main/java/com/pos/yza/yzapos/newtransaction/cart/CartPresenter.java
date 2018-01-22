package com.pos.yza.yzapos.newtransaction.cart;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Lopez on 18/1/18.
 */

public class CartPresenter implements CartContract.Presenter {

    private final CartContract.View mCartView;


    public CartPresenter(@NonNull CartContract.View mCartView) {
        this.mCartView = checkNotNull(mCartView);
        mCartView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void goToProductSelection() {

    }

    @Override
    public void removeProduct() {

    }

    @Override
    public void refreshProducts() {

    }

    @Override
    public void goToCustomerDetails() {

    }
}
