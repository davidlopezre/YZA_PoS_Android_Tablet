package com.pos.yza.yzapos.newtransaction.categoryselection;

import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

/**
 * Created by David Lopez on 24/1/18.
 */

public interface CategorySelectionContract {
    interface View extends BaseView<CategorySelectionContract.Presenter> {
        void showCategories(List<ProductCategory> categories);

    }

    interface Presenter extends BasePresenter {


    }
}
