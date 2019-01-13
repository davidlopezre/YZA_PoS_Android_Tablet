package com.pos.yza.yzapos.adminoptions.editcategory;

import android.support.annotation.NonNull;

import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.CategoriesRepository;

public class EditCategoryPresenter implements EditCategoryContract.Presenter{

    private CategoriesRepository mCategoriesRepository;
    private EditCategoryContract.View mEditCategoryView;
    private ProductCategory mCategory;

    public EditCategoryPresenter(@NonNull CategoriesRepository categoriesRepository,
                                 @NonNull EditCategoryContract.View view,
                                 @NonNull ProductCategory category){
        mCategoriesRepository = categoriesRepository;
        mEditCategoryView = view;
        mEditCategoryView.setPresenter(this);
        mCategory = category;
    }

    @Override
    public void start() {

    }
}
