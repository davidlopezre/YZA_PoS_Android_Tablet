package com.pos.yza.yzapos.newtransaction.cart;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.data.representations.LineItem;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by David Lopez on 18/1/18.
 */

public class CartPresenter implements CartContract.Presenter {

    private final CartContract.View mCartView;

    private CartActions mCurrentAction = CartActions.NO_ACTION;

    public CartPresenter(@NonNull CartContract.View mCartView) {
        this.mCartView = checkNotNull(mCartView);
        mCartView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void goToCategorySelection() {
        mCurrentAction = CartActions.ADD_PRODUCT;
        mCartView.showCategorySelection();
    }

    @Override
    public void removeProduct() {

    }

    @Override
    public void refreshProducts() {

    }

    @Override
    public void goToCustomerDetails() {
        mCurrentAction = CartActions.GO_TO_CUSTOMER_DETAILS;
        mCartView.showCustomerDetails();
    }

    @Override
    public void addLineItem(LineItem lineItem) {
        Log.i("CART", "Adding Product in presenter");
        mCartView.addLineItemToAdapter(lineItem);
    }

    public CartActions getAction() {
        return mCurrentAction;
    }

}
