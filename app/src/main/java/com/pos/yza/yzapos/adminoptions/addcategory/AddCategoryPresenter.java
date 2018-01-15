package com.pos.yza.yzapos.adminoptions.addcategory;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pos.yza.yzapos.adminoptions.additem.AddItemContract;
import com.pos.yza.yzapos.data.representations.CategoryProperty;
import com.pos.yza.yzapos.data.representations.Product;
import com.pos.yza.yzapos.data.representations.ProductCategory;
import com.pos.yza.yzapos.data.representations.ProductProperty;
import com.pos.yza.yzapos.data.source.CategoriesDataSource;
import com.pos.yza.yzapos.data.source.CategoriesRepository;
import com.pos.yza.yzapos.data.source.ProductsRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dlolpez on 31/12/17.
 */

public class AddCategoryPresenter implements AddCategoryContract.Presenter {

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
    }

}
