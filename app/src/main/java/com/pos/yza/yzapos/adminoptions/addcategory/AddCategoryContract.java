package com.pos.yza.yzapos.adminoptions.addcategory;

import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public interface AddCategoryContract {
    interface View extends BaseView<Presenter> {
        void showFeedback();
    }

    interface Presenter extends BasePresenter {
        void confirmCategory(String name, ArrayList<CategoryProperty> properties);
    }
}
