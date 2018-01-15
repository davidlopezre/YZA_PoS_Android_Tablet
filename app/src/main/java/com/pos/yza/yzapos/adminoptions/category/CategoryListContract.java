package com.pos.yza.yzapos.adminoptions.category;

import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.util.BasePresenter;
import com.pos.yza.yzapos.util.BaseView;

import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public interface CategoryListContract {
    interface View extends BaseView<Presenter> {
        void showCategories(List<ProductCategory> categories);
        void showAddCategory();
        void showEditCategory();
        void showDeleteCategory();
    }

    interface Presenter extends BasePresenter {
        void loadCategories();
        void addCategory();
        void deleteCategory(ProductCategory category);
        void editCategory();
    }
}
