package com.pos.yza.yzapos.adminoptions.addcategory;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.source.CategoriesRepository;

import java.util.ArrayList;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class AddCategoryPresenter implements AddCategoryContract.Presenter {

    // TODO (2) : refresh after adding something

    private final AddCategoryContract.View mAddCategoryView;

    private final CategoriesRepository mCategoriesRepository;

    public AddCategoryPresenter(@NonNull CategoriesRepository categoriesRepository,
                                @NonNull AddCategoryContract.View view){
        mCategoriesRepository = categoriesRepository;
        mAddCategoryView = view;

        mAddCategoryView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void confirmCategory(String name, ArrayList<CategoryProperty> properties) {
        Log.i("saveCategory", "in presenter");
        ProductCategory category = new ProductCategory(name, properties);
        mCategoriesRepository.saveCategory(category);
        mAddCategoryView.showFeedback();
    }

}
