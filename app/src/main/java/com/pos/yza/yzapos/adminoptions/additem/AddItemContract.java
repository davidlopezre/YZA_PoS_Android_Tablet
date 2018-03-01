package com.pos.yza.yzapos.adminoptions.additem;

import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public interface AddItemContract {
    interface View extends BaseView<Presenter> {
        void showItemProperties();
        void showCategories(List<ProductCategory> categories);
    }

    interface Presenter extends BasePresenter {
        void confirmItem(ProductCategory category, String unitOfMeasure, String unitPrice,
                         ArrayList<ProductProperty> properties);
        void changeItemProperties();
    }
}
