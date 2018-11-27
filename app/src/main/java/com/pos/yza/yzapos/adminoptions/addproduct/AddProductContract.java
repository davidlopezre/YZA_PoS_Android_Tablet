package com.pos.yza.yzapos.adminoptions.addproduct;

import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public interface AddProductContract {
    interface View extends BaseView<Presenter> {
        void showProductProperties();
        void showCategories(List<ProductCategory> categories);
        void showFeedback();
    }

    interface Presenter extends BasePresenter {
        void confirmProduct(ProductCategory category, String unitOfMeasure, String unitPrice,
                            ArrayList<ProductProperty> properties);
    }
}
