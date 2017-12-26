package com.pos.yza.yzapos.adminoptions;

import com.pos.yza.yzapos.data.Product;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

/**
 * Created by Dlolpez on 26/12/17.
 */

public interface AdminOptionsContract {
    interface View extends BaseView<Presenter> {
        void showProducts(List<Product> products);
        void showProductDetailsUi(String productId);
        void showFilteringPopUpMenu();
        void showAddProduct();
        void showEditProduct();
    }

    interface Presenter extends BasePresenter {
        void loadProducts();
        void addNewProduct();
        void deleteProduct();
        void editProduct();
        void openProductDetails();
        void setFiltering();
    }
}
