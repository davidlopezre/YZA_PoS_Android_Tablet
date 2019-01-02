package com.pos.yza.yzapos.newtransaction.cart;

import com.pos.yza.yzapos.data.representations.LineItem;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

/**
 * Created by Dlolpez on 18/1/18.
 */

public interface CartContract {
    interface View extends BaseView<CartContract.Presenter> {
        void showProductsInCart();
        void showCategorySelection();
        void showCustomerDetails();
        void addLineItemToAdapter(LineItem lineItem);
    }

    interface Presenter extends BasePresenter {
        void goToCategorySelection();
        void removeProduct();
        void refreshProducts();
        void goToCustomerDetails();
        void addLineItem(LineItem lineItem);
    }
}
