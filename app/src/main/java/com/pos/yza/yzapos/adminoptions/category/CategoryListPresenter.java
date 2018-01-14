package com.pos.yza.yzapos.adminoptions.category;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.Staff;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.CategoriesRepository;
import com.pos.yza.yzapos.data.source.StaffDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dlolpez on 12/1/18.
 */

public class CategoryListPresenter implements CategoryListContract.Presenter {
    private final CategoryListContract.View mCategoryListView;

    private final CategoriesRepository mCategoriesRepository;

    public CategoryListPresenter(@NonNull CategoriesRepository categoriesRepository,
                                 @NonNull CategoryListContract.View view){
        mCategoriesRepository = categoriesRepository;
        mCategoryListView = view;

        mCategoryListView.setPresenter(this);
    }

    @Override
    public void start() {
        loadCategories();
    }

    @Override
    public void loadCategories() {
        mCategoriesRepository.getCategories(new CategoriesDataSource.LoadCategoriesCallback() {
            @Override
            public void onCategoriesLoaded(List<ProductCategory> categories) {
                mCategoryListView.showCategories(new ArrayList<ProductCategory>(categories));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void addCategory() {
        mCategoryListView.showAddCategory();
    }

    @Override
    public void deleteCategory(ProductCategory category) {
        String categoryId = Integer.toString(category.getId());
        mCategoriesRepository.deleteCategory(categoryId);
    }

    @Override
    public void editCategory() {

    }

}
