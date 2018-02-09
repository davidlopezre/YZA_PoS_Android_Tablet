package com.pos.yza.yzapos.newtransaction.cart;

import com.pos.yza.yzapos.data.representations.Item;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

/**
 * Created by Dlolpez on 18/1/18.
 */

public interface CartContract {
    interface View extends BaseView<CartContract.Presenter> {
        void showProductsInCart();
        void showCategorySelection();
        void showCustomerDetails();
        void addProductToAdapter(Product product);

    }

    interface Presenter extends BasePresenter {
        void goToCategorySelection();
        void removeProduct();
        void refreshProducts();
        void goToCustomerDetails();
        void addProduct(Product product);
    }
}
