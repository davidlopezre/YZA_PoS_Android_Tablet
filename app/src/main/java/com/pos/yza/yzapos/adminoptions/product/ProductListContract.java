package com.pos.yza.yzapos.adminoptions.product;

import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public interface ProductListContract {
    interface View extends BaseView<Presenter> {
        void setUpSpinnerAdapter(List<ProductCategory> content);
        void showProducts(List<Product> products);
        void showProductDetailsUi(String productId);
        void showFilteringPopUpMenu();
        void showAddProduct();
        void showEditProduct();
        ProductCategory getChosenProductCategory();
    }

    interface Presenter extends BasePresenter {
        void loadProductsByCategory(ProductCategory category);
        void addNewProduct();
        void deleteProduct(Product product);
        void editProduct();
        void openProductDetails();
        void setFiltering();

    }
}
