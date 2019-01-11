package com.pos.yza.yzapos.adminoptions.editproduct;

import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.ArrayList;
import java.util.List;

public interface EditProductContract {
    interface View extends BaseView<Presenter> {
        void showCategories(List<ProductCategory> categories);
    }

    interface Presenter extends BasePresenter {
        String getProductName();
        Product getProduct();
        String getProductCategoryName();
        String getProductPropertyValue(int categoryPropertyId);
        void saveProduct(ProductCategory category, String unitOfMeasure, String unitPrice,
                         ArrayList<ProductProperty> properties);
    }
}
